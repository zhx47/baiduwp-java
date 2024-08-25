package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduPanXpanFileDTO extends BaiduPanBaseDTO {

    @JsonProperty("guid_info")
    private String guidInfo;
    @JsonProperty("list")
    private List<BaiduPanFileMetaDTO> list;
    @JsonProperty("info")
    private List<BaiduPanFileMetaDTO> info;
    @JsonProperty("guid")
    private Integer guid;
}
