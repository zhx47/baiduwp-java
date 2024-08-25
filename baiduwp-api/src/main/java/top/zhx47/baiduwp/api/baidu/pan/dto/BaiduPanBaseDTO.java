package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 百度网盘接口基础响应类
 */
@Data
public class BaiduPanBaseDTO {
    /**
     * 状态码 0-正常 12-部分正常 2-异常 -7-文件或目录无权访问 -9-文件或目录不存在
     */

    @JsonProperty("errno")
    private Integer errno;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("errmsg")
    private String errmsg;
}
