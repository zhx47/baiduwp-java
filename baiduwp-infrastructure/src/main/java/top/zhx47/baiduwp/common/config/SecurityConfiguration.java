package top.zhx47.baiduwp.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Spring Security 配置类，负责定义应用的安全策略和过滤链。
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    // 白名单 URL，允许未认证的访问
    private static final String[] WHITE_LIST_URL = {"/h2-console/**", "/login"};

    // 注入自定义的 JWT 认证过滤器
    private final JwtAuthenticationFilter jwtAuthFilter;
    // 注入自定义的 AuthenticationProvider，用于处理身份验证逻辑
    private final AuthenticationProvider authenticationProvider;
    // 注入自定义的登出处理器
    private final LogoutHandler logoutHandler;

    /**
     * 配置安全过滤链。
     *
     * @param http HttpSecurity 配置对象
     * @return 配置好的 SecurityFilterChain 对象
     * @throws Exception 可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF 保护
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                // 配置请求授权
                .authorizeHttpRequests(req ->
                        // 白名单内的 URL 允许所有人访问, 其他所有请求都需要认证
                        req.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
                // 配置会话管理为无状态（Stateless）
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // 设置自定义的 AuthenticationProvider
                .authenticationProvider(authenticationProvider)
                // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 认证过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置登出行为
                .logout(
                        logout -> logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        // 构建并返回安全过滤链
        return http.build();
    }
}