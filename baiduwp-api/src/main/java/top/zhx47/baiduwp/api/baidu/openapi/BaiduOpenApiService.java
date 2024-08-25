package top.zhx47.baiduwp.api.baidu.openapi;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import org.springframework.stereotype.Component;
import retrofit2.http.GET;
import retrofit2.http.Query;
import top.zhx47.baiduwp.api.baidu.openapi.dto.BaiduOpenApiTokenDTO;

@Component
@RetrofitClient(baseUrl = "${baidu.openapi}")
public interface BaiduOpenApiService {

    @GET("oauth/2.0/token")
    BaiduOpenApiTokenDTO token(@Query("grant_type") String grantType, @Query("refresh_token") String refreshToken, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

}
