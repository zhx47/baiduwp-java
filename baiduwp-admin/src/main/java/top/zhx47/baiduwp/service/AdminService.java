package top.zhx47.baiduwp.service;

import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanFileMetaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanQuotaDTO;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.controller.command.BindTokenCommand;
import top.zhx47.baiduwp.controller.command.DownloadCommand;
import top.zhx47.baiduwp.controller.command.GetFileListCommand;
import top.zhx47.baiduwp.controller.command.LoginCommand;

import java.util.List;

public interface AdminService {
    SystemLoginUser getUserInfo();

    BaiduPanQuotaDTO quota();

    List<BaiduPanFileMetaDTO> getFileList(GetFileListCommand command);

    String login(LoginCommand command);

    void bindToken(BindTokenCommand command);

    List<BaiduPanFileMetaDTO> download(DownloadCommand command);
}
