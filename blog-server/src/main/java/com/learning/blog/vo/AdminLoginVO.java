package com.learning.blog.vo;

import lombok.Data;

@Data
public class AdminLoginVO {

    private String token;

    private String tokenType;

    private AdminUserInfoVO userInfo;
}
