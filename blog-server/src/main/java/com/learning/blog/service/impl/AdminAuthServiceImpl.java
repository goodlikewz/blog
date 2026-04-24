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

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    private final BlogUserMapper blogUserMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminAuthServiceImpl(BlogUserMapper blogUserMapper, JwtUtils jwtUtils) {
        this.blogUserMapper = blogUserMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AdminLoginVO login(AdminLoginRequest request) {
        BlogUser user = blogUserMapper.selectOne(new LambdaQueryWrapper<BlogUser>()
                .eq(BlogUser::getUsername, request.getUsername())
                .last("limit 1"));

        if (user == null) {
            throw new BusinessException(400, "用户名或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(403, "当前账号已被禁用");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(400, "用户名或密码错误");
        }

        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        AdminUserInfoVO userInfo = new AdminUserInfoVO();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());

        AdminLoginVO loginVO = new AdminLoginVO();
        loginVO.setToken(token);
        loginVO.setTokenType("Bearer");
        loginVO.setUserInfo(userInfo);
        return loginVO;
    }

    @Override
    public AdminUserInfoVO getCurrentUser(Long userId, String username) {
        BlogUser user = blogUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        AdminUserInfoVO userInfo = new AdminUserInfoVO();
        userInfo.setId(userId);
        userInfo.setUsername(username);
        userInfo.setNickname(user.getNickname());
        return userInfo;
    }
}
