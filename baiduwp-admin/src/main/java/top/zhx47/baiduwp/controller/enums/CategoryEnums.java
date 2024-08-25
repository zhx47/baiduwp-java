package top.zhx47.baiduwp.controller.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;

@Getter
public enum CategoryEnums {
    VIDEO("video", BaiduPanCategoryEnum.VIDEO),
    MUSIC("music", BaiduPanCategoryEnum.MUSIC),
    IMAGE("image", BaiduPanCategoryEnum.IMAGE),
    DOC("doc", BaiduPanCategoryEnum.DOC),
    OTHER("others", BaiduPanCategoryEnum.OTHER);

    @JsonValue
    private final String value;
    private final BaiduPanCategoryEnum mapping;

    CategoryEnums(String value, BaiduPanCategoryEnum mapping) {
        this.value = value;
        this.mapping = mapping;
    }

    @JsonCreator
    public static CategoryEnums forValue(String value) {
        for (CategoryEnums category : CategoryEnums.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        // 默认获取全部
        return null;
    }
}