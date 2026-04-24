package com.learning.blog.controller.admin;

import com.learning.blog.common.ApiResponse;
import com.learning.blog.dto.AdminLoginRequest;
import com.learning.blog.service.AdminAuthService;
import com.learning.blog.vo.AdminLoginVO;
import com.learning.blog.vo.AdminUserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ApiResponse<AdminLoginVO> login(@Valid @RequestBody AdminLoginRequest request) {
        return ApiResponse.success("登录成功", adminAuthService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success("退出登录成功", null);
    }

    @GetMapping("/me")
    public ApiResponse<AdminUserInfoVO> currentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("currentUserId");
        String username = (String) request.getAttribute("currentUsername");
        return ApiResponse.success(adminAuthService.getCurrentUser(userId, username));
    }
}
