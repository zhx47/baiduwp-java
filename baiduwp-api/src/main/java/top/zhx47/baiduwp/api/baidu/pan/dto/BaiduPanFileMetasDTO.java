package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduPanFileMetasDTO extends BaiduPanBaseDTO {

    @JsonProperty("info")
    private List<BaiduPanFileMetaDTO> info;

}
