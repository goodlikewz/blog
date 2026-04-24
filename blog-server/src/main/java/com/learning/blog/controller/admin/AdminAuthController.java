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

/**
 * 后台认证控制器。
 *
 * 负责后台登录、退出登录和当前用户信息获取。
 */
@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    /**
     * 管理员登录。
     *
     * @param request 登录请求参数
     * @return 登录结果，包含 Token 和当前用户信息
     */
    @PostMapping("/login")
    public ApiResponse<AdminLoginVO> login(@Valid @RequestBody AdminLoginRequest request) {
        return ApiResponse.success("登录成功", adminAuthService.login(request));
    }

    /**
     * 管理员退出登录。
     *
     * 当前阶段采用 JWT，无服务端会话，因此这里主要用于前端形成统一退出流程。
     *
     * @return 退出登录结果
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success("退出登录成功", null);
    }

    /**
     * 获取当前登录用户信息。
     *
     * @param request 当前请求对象
     * @return 当前登录用户信息
     */
    @GetMapping("/me")
    public ApiResponse<AdminUserInfoVO> currentUser(HttpServletRequest request) {
        // 从拦截器写入的请求上下文中读取当前用户信息。
        Long userId = (Long) request.getAttribute("currentUserId");
        String username = (String) request.getAttribute("currentUsername");
        return ApiResponse.success(adminAuthService.getCurrentUser(userId, username));
    }
}
