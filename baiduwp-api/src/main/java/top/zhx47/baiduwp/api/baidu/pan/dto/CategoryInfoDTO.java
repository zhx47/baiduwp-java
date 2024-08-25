package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryInfoDTO extends BaiduPanBaseDTO {
    @JsonProperty("info")
    private InfoDTO info;

    public Integer getCategoryCount(BaiduPanCategoryEnum category) {
        return getInfo().getCategoryInfoByEnum(category);
    }

    @Data
    public static class InfoDTO {
        @JsonProperty("1")
        private CategoryInfo videoCategoryInfo;
        @JsonProperty("2")
        private CategoryInfo musicCategoryInfo;
        @JsonProperty("3")
        private CategoryInfo imageCategoryInfo;
        @JsonProperty("4")
        private CategoryInfo docCategoryInfo;
        @JsonProperty("5")
        private CategoryInfo appCategoryInfo;
        @JsonProperty("6")
        private CategoryInfo otherCategoryInfo;
        @JsonProperty("7")
        private CategoryInfo btCategoryInfo;

        public Integer getCategoryInfoByEnum(BaiduPanCategoryEnum category) {
            return switch (category) {
                case VIDEO -> videoCategoryInfo.getCount();
                case MUSIC -> musicCategoryInfo.getCount();
                case IMAGE -> imageCategoryInfo.getCount();
                case DOC -> docCategoryInfo.getCount();
                case APP -> appCategoryInfo.getCount();
                case OTHER -> otherCategoryInfo.getCount();
                case BT -> btCategoryInfo.getCount();
            };
        }

        @NoArgsConstructor
        @Data
        static class CategoryInfo {
            @JsonProperty("real_server_mtime_2")
            private String realServerMtime2;
            @JsonProperty("size")
            private Integer size;
            @JsonProperty("total")
            private Integer total;
            @JsonProperty("count")
            private Integer count;
        }
    }

}
