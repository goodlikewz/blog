package com.learning.blog.interceptor;

import com.learning.blog.common.exception.BusinessException;
import com.learning.blog.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 登录拦截器。
 *
 * 用于校验后台请求中的 Token，并把当前用户信息写入请求上下文。
 */
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtAuthInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 在进入后台接口前校验 Token。
     *
     * @param request 当前请求对象
     * @param response 当前响应对象
     * @param handler 当前处理器
     * @return 是否放行请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头读取登录凭证。
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            throw new BusinessException(401, "未登录或 Token 缺失");
        }

        // 去掉 Bearer 前缀，得到真正的 Token 内容。
        String token = authorization.substring(7);
        // 解析 Token 并校验签名、过期时间等信息。
        Claims claims = jwtUtils.parseToken(token);
        // Token 校验通过后，把当前用户信息挂到 request，方便控制器直接读取。
        request.setAttribute("currentUserId", Long.valueOf(claims.getSubject()));
        request.setAttribute("currentUsername", claims.get("username", String.class));
        return true;
    }
}
