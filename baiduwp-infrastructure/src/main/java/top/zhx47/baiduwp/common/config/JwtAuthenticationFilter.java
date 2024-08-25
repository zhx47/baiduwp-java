package top.zhx47.baiduwp.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.common.cache.SystemLoginUserCache;

import java.io.IOException;

/**
 * JWT 身份验证过滤器，继承自 OncePerRequestFilter，确保每个请求只执行一次。
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT 服务，用于处理 JWT 的生成和验证
    private final JwtService jwtService;
    private final SystemLoginUserCache systemLoginUserCache;

    /**
     * 过滤器的核心逻辑，处理每个请求的身份验证。
     *
     * @param request     HTTP 请求
     * @param response    HTTP 响应
     * @param filterChain 过滤器链
     * @throws ServletException 可能抛出的异常
     * @throws IOException      可能抛出的异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // JWT 令牌
        final String jwt = getTokenFromRequest(request);

        if (StringUtils.hasText(jwt)) {
            String username = jwtService.extractUsername(jwt);
            SystemLoginUser systemLoginUser = systemLoginUserCache.get(username);
            if (systemLoginUser != null && jwtService.isTokenValid(jwt, systemLoginUser)) {
                // 创建身份验证令牌
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(systemLoginUser, null, systemLoginUser.getAuthorities());
                // 设置额外的请求详细信息
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将认证信息存入 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
}