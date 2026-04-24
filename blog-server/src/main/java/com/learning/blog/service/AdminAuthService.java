package com.learning.blog.service;

import com.learning.blog.dto.AdminLoginRequest;
import com.learning.blog.vo.AdminLoginVO;
import com.learning.blog.vo.AdminUserInfoVO;

public interface AdminAuthService {

    AdminLoginVO login(AdminLoginRequest request);

    AdminUserInfoVO getCurrentUser(Long userId, String username);
}
