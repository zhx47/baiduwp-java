package top.zhx47.baiduwp.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.common.web.TokenInfo;
import top.zhx47.baiduwp.common.exception.ApiException;
import top.zhx47.baiduwp.common.exception.error.ErrorCode;

public class AuthenticationUtils {

    private AuthenticationUtils() {
    }

    public static String getAccessToken() {
        TokenInfo token = getSystemLoginUser().getToken();
        if (token == null) {
            throw new ApiException(ErrorCode.Business.BAIDUPAN_EXPIRE);
        }
        return token.getAccessToken();
    }

    public static String getRefreshToken() {
        TokenInfo token = getSystemLoginUser().getToken();
        if (token == null) {
            throw new ApiException(ErrorCode.Business.BAIDUPAN_EXPIRE);
        }
        return token.getRefreshToken();
    }

    /**
     * 用户ID
     **/
    public static String getUsername() {
        return getSystemLoginUser().getUsername();
    }

    /**
     * 获取系统用户
     **/
    public static SystemLoginUser getSystemLoginUser() {
        return (SystemLoginUser) getAuthentication().getPrincipal();
    }


    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
