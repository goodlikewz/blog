package com.learning.blog.interceptor;

import com.learning.blog.common.exception.BusinessException;
import com.learning.blog.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtAuthInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            throw new BusinessException(401, "未登录或 Token 缺失");
        }

        String token = authorization.substring(7);
        Claims claims = jwtUtils.parseToken(token);
        request.setAttribute("currentUserId", Long.valueOf(claims.getSubject()));
        request.setAttribute("currentUsername", claims.get("username", String.class));
        return true;
    }
}
