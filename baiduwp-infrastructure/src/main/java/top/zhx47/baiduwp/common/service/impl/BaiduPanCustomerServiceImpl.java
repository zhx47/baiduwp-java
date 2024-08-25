package top.zhx47.baiduwp.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zhx47.baiduwp.api.baidu.pan.BaiduPanService;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanFileMetaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanQuotaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanXpanNasDTO;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;
import top.zhx47.baiduwp.common.AuthenticationUtils;
import top.zhx47.baiduwp.common.JsonUtils;
import top.zhx47.baiduwp.common.service.BaiduPanCustomerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaiduPanCustomerServiceImpl implements BaiduPanCustomerService {
    private final BaiduPanService baiduPanService;

    @Override
    public BaiduPanXpanNasDTO getUserInfo() {
        return baiduPanService.getXpanNas("uinfo", AuthenticationUtils.getAccessToken());
    }

    @Override
    public BaiduPanQuotaDTO quota() {
        return baiduPanService.getQuota(AuthenticationUtils.getAccessToken());
    }

    @Override
    public List<BaiduPanFileMetaDTO> getFileList(String dir) {
        return baiduPanService.getXpanFile("list", null, dir, null, null, AuthenticationUtils.getAccessToken()).getList();
    }

    @Override
    public Integer getCategoryFileCount(BaiduPanCategoryEnum category, String parentPath) {
        return baiduPanService.getCategoryInfo(category, parentPath, AuthenticationUtils.getAccessToken()).getCategoryCount(category);
    }

    @Override
    public List<BaiduPanFileMetaDTO> getCategoryFileList(BaiduPanCategoryEnum category, String dir) {
        return baiduPanService.getXpanMultimedia("categorylist", category, dir, "1", AuthenticationUtils.getAccessToken()).getList();
    }

    @Override
    public List<BaiduPanFileMetaDTO> getFileListByKey(BaiduPanCategoryEnum category, String dir, String key) {
        return baiduPanService.getXpanFile("search", category, dir, key, "1", AuthenticationUtils.getAccessToken()).getList();
    }

    @Override
    public List<BaiduPanFileMetaDTO> download(List<String> command) {
        return baiduPanService.getFilemetas(JsonUtils.toJsonString(command), 1, 5, "dlna", AuthenticationUtils.getAccessToken()).getInfo();
    }

}
