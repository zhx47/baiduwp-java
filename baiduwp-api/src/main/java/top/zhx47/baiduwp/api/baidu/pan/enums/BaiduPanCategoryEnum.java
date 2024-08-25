package top.zhx47.baiduwp.api.baidu.pan.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

@Getter
public enum BaiduPanCategoryEnum {

    VIDEO("视频", "1"),
    MUSIC("音频", "2"),
    IMAGE("图片", "3"),
    DOC("文档", "4"),
    APP("应用", "5"),
    OTHER("其他", "6"),
    BT("种子", "7");

    private final String name;
    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }

    BaiduPanCategoryEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @JsonCreator
    public static BaiduPanCategoryEnum forValue(String value) {
        for (BaiduPanCategoryEnum category : BaiduPanCategoryEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid BaiduPanCategory value: " + value);
    }
}