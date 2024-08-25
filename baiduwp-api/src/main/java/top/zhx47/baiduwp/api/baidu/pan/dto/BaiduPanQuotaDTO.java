package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduPanQuotaDTO extends BaiduPanBaseDTO {

    @JsonProperty("expire")
    private Boolean expire;
    @JsonProperty("free")
    private Integer free;
    @JsonProperty("is_show_window")
    private Integer isShowWindow;
    @JsonProperty("newno")
    private String newno;
    @JsonProperty("recmd_vip")
    private String recmdVip;
    @JsonProperty("recyclestatus")
    private Integer recyclestatus;
    @JsonProperty("sbox_total")
    private Integer sboxTotal;
    @JsonProperty("sbox_used")
    private Integer sboxUsed;
    @JsonProperty("server_time")
    private Integer serverTime;
    @JsonProperty("total")
    private Long total;
    @JsonProperty("used")
    private Long used;
}
