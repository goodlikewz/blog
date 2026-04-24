package com.learning.blog.util;

import com.learning.blog.common.exception.BusinessException;
import com.learning.blog.config.BlogJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final BlogJwtProperties blogJwtProperties;

    public JwtUtils(BlogJwtProperties blogJwtProperties) {
        this.blogJwtProperties = blogJwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(blogJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String username) {
        Instant now = Instant.now();
        Instant expireTime = now.plus(blogJwtProperties.getExpireHours(), ChronoUnit.HOURS);
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expireTime))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            throw new BusinessException(401, "Token 无效或已过期");
        }
    }
}
