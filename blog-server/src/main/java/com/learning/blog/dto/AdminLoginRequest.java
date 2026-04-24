package com.learning.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 后台登录请求参数。
 */
@Data
public class AdminLoginRequest {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
