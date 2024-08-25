package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduPanXpanNasDTO extends BaiduPanBaseDTO {

    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("baidu_name")
    private String baiduName;
    @JsonProperty("netdisk_name")
    private String netdiskName;
    @JsonProperty("uk")
    private Long uk;
    @JsonProperty("vip_type")
    private Integer vipType;
}
