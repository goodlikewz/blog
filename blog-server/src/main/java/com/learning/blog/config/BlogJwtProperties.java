package com.learning.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "blog.jwt")
public class BlogJwtProperties {

    private String secret;
    private long expireHours;
}
