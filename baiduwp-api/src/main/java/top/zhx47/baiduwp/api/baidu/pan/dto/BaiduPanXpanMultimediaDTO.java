package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduPanXpanMultimediaDTO extends BaiduPanBaseDTO {

    @JsonProperty("cursor")
    private Integer cursor;
    @JsonProperty("has_more")
    private Integer hasMore;
    @JsonProperty("list")
    private List<BaiduPanFileMetaDTO> list;
}
