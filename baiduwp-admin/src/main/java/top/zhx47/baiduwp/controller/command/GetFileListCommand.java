package top.zhx47.baiduwp.controller.command;

import lombok.Data;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;
import top.zhx47.baiduwp.controller.enums.CategoryEnums;

@Data
public class GetFileListCommand {
    private CategoryEnums category;
    private String dir;
    private String key;

    public BaiduPanCategoryEnum getCategoryMapping() {
        return category == null ? null : category.getMapping();
    }
}
