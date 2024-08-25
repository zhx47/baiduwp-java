package top.zhx47.baiduwp.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.zhx47.baiduwp.common.AuthenticationUtils;
import top.zhx47.baiduwp.common.cache.SystemLoginUserCache;

/**
 * 退出服务类，负责处理用户退出登录的逻辑。
 * 实现了 Spring Security 的 LogoutHandler 接口。
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final SystemLoginUserCache systemLoginUserCache;

    /**
     * 执行用户登出操作。
     *
     * @param request        HTTP 请求对象
     * @param response       HTTP 响应对象
     * @param authentication 当前用户的认证信息
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String username = AuthenticationUtils.getUsername();
        if (StringUtils.hasText(username)) {
            systemLoginUserCache.invalidate(username);
            SecurityContextHolder.clearContext();
        }
    }
}