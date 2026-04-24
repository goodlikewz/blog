package com.learning.blog.vo;

import lombok.Data;

/**
 * 后台登录返回结果。
 */
@Data
public class AdminLoginVO {

    private String token;

    private String tokenType;

    private AdminUserInfoVO userInfo;
}
