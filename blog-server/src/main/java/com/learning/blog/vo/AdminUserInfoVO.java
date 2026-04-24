package com.learning.blog.vo;

import lombok.Data;

/**
 * 当前登录用户信息。
 */
@Data
public class AdminUserInfoVO {

    private Long id;

    private String username;

    private String nickname;
}
