package top.zhx47.baiduwp.api.baidu.pan;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import org.springframework.stereotype.Component;
import retrofit2.http.GET;
import retrofit2.http.Query;
import top.zhx47.baiduwp.api.baidu.interceptor.BaiduPanInterceptor;
import top.zhx47.baiduwp.api.baidu.pan.dto.*;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;

@Component
@Intercept(handler = BaiduPanInterceptor.class)
@RetrofitClient(baseUrl = "${baidu.pan}")
public interface BaiduPanService {

    @GET("api/quota")
    BaiduPanQuotaDTO getQuota(@Query("access_token") String accessToken);

    @GET("api/categoryinfo")
    CategoryInfoDTO getCategoryInfo(@Query("category") BaiduPanCategoryEnum category, @Query("parent_path") String parentPath, @Query("access_token") String accessToken);

    @GET("api/filemetas")
    BaiduPanFileMetasDTO getFilemetas(@Query("target") String target, @Query("dlink") Integer dlink, @Query("web") Integer web, @Query("origin") String origin, @Query("access_token") String access_token);

    @GET("rest/2.0/xpan/nas")
    BaiduPanXpanNasDTO getXpanNas(@Query("method") String method, @Query("access_token") String accessToken);

    @GET("rest/2.0/xpan/file")
    BaiduPanXpanFileDTO getXpanFile(@Query("method") String method, @Query("category") BaiduPanCategoryEnum category, @Query("dir") String dir, @Query("key") String key, @Query("recursion") String recursion, @Query("access_token") String accessToken);

    @GET("rest/2.0/xpan/multimedia")
    BaiduPanXpanMultimediaDTO getXpanMultimedia(@Query("method") String method, @Query("category") BaiduPanCategoryEnum category, @Query("parent_path") String parentPath, @Query("recursion") String recursion, @Query("access_token") String accessToken);

}
