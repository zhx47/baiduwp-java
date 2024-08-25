package top.zhx47.baiduwp.api.baidu.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduOpenApiTokenDTO extends BaiduOpenApiErrorDTO {

    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("session_secret")
    private String sessionSecret;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("scope")
    private String scope;
}
