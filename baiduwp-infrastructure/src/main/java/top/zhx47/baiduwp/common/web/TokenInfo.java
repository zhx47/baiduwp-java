package top.zhx47.baiduwp.common.web;

import lombok.Data;
import top.zhx47.baiduwp.database.entity.TokenEntity;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;

import java.time.LocalDateTime;

@Data
public class TokenInfo {

    private String refreshToken;
    private String accessToken;
    private LocalDateTime expiresIn;

    public TokenInfo(TokenEntity token) {
        if (YesOrNoEnum.NORMAL == token.getAccountStatus()) {
            this.refreshToken = token.getRefreshToken();
            this.accessToken = token.getAccessToken();
            this.expiresIn = token.getExpiresIn();
        }
    }
}