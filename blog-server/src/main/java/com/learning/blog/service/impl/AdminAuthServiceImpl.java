package com.learning.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.learning.blog.common.exception.BusinessException;
import com.learning.blog.dto.AdminLoginRequest;
import com.learning.blog.entity.BlogUser;
import com.learning.blog.mapper.BlogUserMapper;
import com.learning.blog.service.AdminAuthService;
import com.learning.blog.util.JwtUtils;
import com.learning.blog.vo.AdminLoginVO;
import com.learning.blog.vo.AdminUserInfoVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 后台认证服务实现。
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    private final BlogUserMapper blogUserMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminAuthServiceImpl(BlogUserMapper blogUserMapper, JwtUtils jwtUtils) {
        this.blogUserMapper = blogUserMapper;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 执行管理员登录。
     *
     * @param request 登录请求参数
     * @return 登录结果
     */
    @Override
    public AdminLoginVO login(AdminLoginRequest request) {
        // 根据用户名查询后台管理员账号。
        BlogUser user = blogUserMapper.selectOne(new LambdaQueryWrapper<BlogUser>()
                .eq(BlogUser::getUsername, request.getUsername())
                .last("limit 1"));

        // 登录失败时统一提示用户名或密码错误，避免暴露账号是否存在。
        if (user == null) {
            throw new BusinessException(400, "用户名或密码错误");
        }
        // 禁用账号不能登录后台。
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(403, "当前账号已被禁用");
        }
        // 使用 BCrypt 校验明文密码和数据库中的密文密码是否匹配。
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(400, "用户名或密码错误");
        }

        // 登录成功后签发 JWT，前端后续通过该 Token 访问后台接口。
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        // 组装当前登录用户信息，避免把密码等敏感字段返回给前端。
        AdminUserInfoVO userInfo = new AdminUserInfoVO();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());

        // 组装登录响应，把 Token 和用户信息一起返回。
        AdminLoginVO loginVO = new AdminLoginVO();
        loginVO.setToken(token);
        loginVO.setTokenType("Bearer");
        loginVO.setUserInfo(userInfo);
        return loginVO;
    }

    /**
     * 获取当前登录用户信息。
     *
     * @param userId 当前用户 ID
     * @param username 当前用户名
     * @return 当前用户信息
     */
    @Override
    public AdminUserInfoVO getCurrentUser(Long userId, String username) {
        // 根据 Token 中的用户 ID 查询最新用户信息。
        BlogUser user = blogUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        // 只返回前端需要展示的基础信息。
        AdminUserInfoVO userInfo = new AdminUserInfoVO();
        userInfo.setId(userId);
        userInfo.setUsername(username);
        userInfo.setNickname(user.getNickname());
        return userInfo;
    }
}
