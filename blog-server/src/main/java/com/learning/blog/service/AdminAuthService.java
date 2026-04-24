package com.learning.blog.service;

import com.learning.blog.dto.AdminLoginRequest;
import com.learning.blog.vo.AdminLoginVO;
import com.learning.blog.vo.AdminUserInfoVO;

/**
 * 后台认证服务接口。
 */
public interface AdminAuthService {

    /**
     * 执行管理员登录。
     *
     * @param request 登录请求参数
     * @return 登录结果
     */
    AdminLoginVO login(AdminLoginRequest request);

    /**
     * 获取当前登录用户信息。
     *
     * @param userId 当前用户 ID
     * @param username 当前用户名
     * @return 当前用户信息
     */
    AdminUserInfoVO getCurrentUser(Long userId, String username);
}
