package top.zhx47.baiduwp.database.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum YesOrNoEnum {

    /**
     * 用户账户状态
     */
    NORMAL(1, "正常"),
    DISABLE(0, "过期");

    @EnumValue
    private final int value;
    private final String description;

    YesOrNoEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
