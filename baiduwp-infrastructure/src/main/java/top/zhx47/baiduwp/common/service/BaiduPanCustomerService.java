package top.zhx47.baiduwp.common.service;

import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanFileMetaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanQuotaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanXpanNasDTO;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;

import java.util.List;

public interface BaiduPanCustomerService {

    BaiduPanXpanNasDTO getUserInfo();

    BaiduPanQuotaDTO quota();

    List<BaiduPanFileMetaDTO> getFileList(String dir);

    Integer getCategoryFileCount(BaiduPanCategoryEnum category, String parentPath);

    List<BaiduPanFileMetaDTO> getCategoryFileList(BaiduPanCategoryEnum category, String dir);

    List<BaiduPanFileMetaDTO> getFileListByKey(BaiduPanCategoryEnum category, String dir, String key);

    List<BaiduPanFileMetaDTO> download(List<String> command);
}
