package com.lightblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightblog.common.ApiResponse;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.User;
import com.lightblog.service.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    private static final Pattern ARTICLE_DETAIL = Pattern.compile("^/api/article/\\d+$");
    private static final Pattern COMMENT_LIST = Pattern.compile("^/api/comment/list/\\d+$");
    private static final Pattern SPACE_GET = Pattern.compile("^/api/space/\\d+$");
    private static final Pattern SPACE_ARTICLES = Pattern.compile("^/api/space/\\d+/articles$");

    public AuthInterceptor(TokenService tokenService, ObjectMapper objectMapper) {
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = resolveToken(request);
        if (token != null) {
            User user = tokenService.getUser(token);
            if (user != null && user.status != null && user.status == 1) {
                tokenService.touch(token);
                AuthContext.set(user);
            }
        }

        if (requiresAuth(request) && AuthContext.get() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(401, "请先登录")));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AuthContext.clear();
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.trim().isEmpty()) {
            return null;
        }
        if (header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return header;
    }

    private boolean requiresAuth(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (uri.startsWith("/api/admin")) {
            return true;
        }
        if (uri.equals("/api/user/login") || uri.equals("/api/user/register")) {
            return false;
        }
        if (uri.startsWith("/api/user")) {
            return true;
        }
        if (uri.equals("/api/article/list") && "GET".equalsIgnoreCase(method)) {
            return false;
        }
        if (ARTICLE_DETAIL.matcher(uri).matches() && "GET".equalsIgnoreCase(method)) {
            return false;
        }
        if (COMMENT_LIST.matcher(uri).matches() && "GET".equalsIgnoreCase(method)) {
            return false;
        }
        if ((SPACE_GET.matcher(uri).matches() || SPACE_ARTICLES.matcher(uri).matches()) && "GET".equalsIgnoreCase(method)) {
            return false;
        }
        if (uri.startsWith("/api/article") || uri.startsWith("/api/category")
                || uri.startsWith("/api/comment") || uri.startsWith("/api/follow")
                || uri.startsWith("/api/space")) {
            return true;
        }
        return false;
    }
}
