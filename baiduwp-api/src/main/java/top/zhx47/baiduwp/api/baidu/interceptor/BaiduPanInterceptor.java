package top.zhx47.baiduwp.api.baidu.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import okhttp3.*;
import org.springframework.stereotype.Component;
import top.zhx47.baiduwp.api.baidu.JsonUtils;
import top.zhx47.baiduwp.api.baidu.exception.BaiduPanApiExpireException;
import top.zhx47.baiduwp.api.baidu.exception.BaiduPanApiOtherException;

import java.io.IOException;

@Component
public class BaiduPanInterceptor extends BasePathMatchInterceptor {

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();

        String url = request.url().toString();
        try (Response response = chain.proceed(request)) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new BaiduPanApiOtherException(url + ": 请求没有返回值");
            }

            String responseBodyString = responseBody.string();
            JsonNode rootNode = JsonUtils.readTree(responseBodyString);

            // 检查是否存在 code 字段并读取它的值
            if (rootNode.has("errno")) {
                int code = rootNode.get("errno").asInt();
                if (code != 0 && code != -6) {
                    throw new BaiduPanApiOtherException(url + ": 其他异常，异常码: " + code);
                }
                if (code == -6) {
                    throw new BaiduPanApiExpireException("access token 过期!!!");
                }
            }

            MediaType contentType = responseBody.contentType();
            ResponseBody newResponseBody = ResponseBody.create(responseBodyString, contentType);
            return response.newBuilder().body(newResponseBody).build();
        }
    }
}
