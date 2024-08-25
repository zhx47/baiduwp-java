package top.zhx47.baiduwp.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zhx47.baiduwp.api.baidu.openapi.BaiduOpenApiService;
import top.zhx47.baiduwp.api.baidu.openapi.dto.BaiduOpenApiTokenDTO;
import top.zhx47.baiduwp.common.AuthenticationUtils;
import top.zhx47.baiduwp.common.cache.SystemLoginUserCache;
import top.zhx47.baiduwp.common.service.BaiduOpenApiCustomerService;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.database.entity.TokenEntity;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;
import top.zhx47.baiduwp.database.service.TokenService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BaiduOpenApiCustomerServiceImpl implements BaiduOpenApiCustomerService {

    private final BaiduOpenApiService baiduOpenApiService;
    private final TokenService tokenService;
    private final SystemLoginUserCache systemLoginUserCache;

    @Override
    public void refreshAccessToken(String refreshToken) {
        String username = AuthenticationUtils.getUsername();
        TokenEntity token = tokenService.getById(username);
        SystemLoginUser systemLoginUser = AuthenticationUtils.getSystemLoginUser();
        try {
            BaiduOpenApiTokenDTO tokenInfo = baiduOpenApiService.token("refresh_token", refreshToken, "iYCeC9g08h5vuP9UqvPHKKSVrKFXGa1v", "jXiFMOPVPCWlO2M5CwWQzffpNPaGTRBG");

            token.setAccountStatus(YesOrNoEnum.NORMAL);
            token.setAccessToken(tokenInfo.getAccessToken());
            token.setRefreshToken(tokenInfo.getRefreshToken());
            token.setExpiresIn(LocalDateTime.now().plusSeconds(tokenInfo.getExpiresIn()));
            tokenService.updateById(token);

            systemLoginUser.updateToken(token);
            systemLoginUserCache.put(systemLoginUser.getUsername(), systemLoginUser);
        } catch (Exception e) {
            token.setAccountStatus(YesOrNoEnum.DISABLE);
            tokenService.updateById(token);
            systemLoginUser.updateToken(null);
            systemLoginUserCache.put(systemLoginUser.getUsername(), systemLoginUser);
        }
    }
}
