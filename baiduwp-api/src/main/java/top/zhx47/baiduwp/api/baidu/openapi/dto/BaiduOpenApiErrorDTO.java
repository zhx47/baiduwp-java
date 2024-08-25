package top.zhx47.baiduwp.api.baidu.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 百度开放接口基础响应类，接口无异常字段不应有值
 */
@Data
public class BaiduOpenApiErrorDTO {
    @JsonProperty("error")
    private String error;
    @JsonProperty("error_description")
    private String error_description;
}
