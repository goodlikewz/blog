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

/**
 * JWT 工具类。
 *
 * 负责 Token 的生成和解析。
 */
@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final BlogJwtProperties blogJwtProperties;

    public JwtUtils(BlogJwtProperties blogJwtProperties) {
        this.blogJwtProperties = blogJwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(blogJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成后台登录 Token。
     *
     * @param userId 用户 ID
     * @param username 用户名
     * @return JWT Token
     */
    public String generateToken(Long userId, String username) {
        // 记录 Token 签发时间。
        Instant now = Instant.now();
        // 根据配置计算 Token 过期时间。
        Instant expireTime = now.plus(blogJwtProperties.getExpireHours(), ChronoUnit.HOURS);
        // 把用户 ID 放入 subject，把用户名放入自定义 claim。
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expireTime))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析并校验 Token。
     *
     * @param token JWT Token
     * @return Token 中的声明信息
     */
    public Claims parseToken(String token) {
        try {
            // 校验签名并解析 Token 内容。
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            // 解析失败统一按未登录处理，避免把底层异常细节暴露给前端。
            throw new BusinessException(401, "Token 无效或已过期");
        }
    }
}
