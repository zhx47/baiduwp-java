package top.zhx47.baiduwp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanFileMetaDTO;
import top.zhx47.baiduwp.common.ResponseDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanQuotaDTO;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.controller.command.BindTokenCommand;
import top.zhx47.baiduwp.controller.command.DownloadCommand;
import top.zhx47.baiduwp.controller.command.GetFileListCommand;
import top.zhx47.baiduwp.controller.command.LoginCommand;
import top.zhx47.baiduwp.service.AdminService;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RequestMapping("/login")
    public ResponseDTO<String> login(@RequestBody LoginCommand command) {
        String token = adminService.login(command);
        return ResponseDTO.ok(token);
    }

    @RequestMapping("/getUseSpace")
    public ResponseDTO<BaiduPanQuotaDTO> getUseSpace() {
        BaiduPanQuotaDTO quota = adminService.quota();
        return ResponseDTO.ok(quota);
    }

    @RequestMapping("/getFileList")
    public ResponseDTO<List<BaiduPanFileMetaDTO>> getFileList(@RequestBody GetFileListCommand command) {
        List<BaiduPanFileMetaDTO> fileList = adminService.getFileList(command);
        return ResponseDTO.ok(fileList);
    }

    @RequestMapping("/getUserInfo")
    public ResponseDTO<SystemLoginUser> getUserInfo() {
        SystemLoginUser userInfo = adminService.getUserInfo();
        return ResponseDTO.ok(userInfo);
    }

    @RequestMapping("/bindToken")
    public ResponseDTO<String> bindToken(@RequestBody BindTokenCommand command) {
        adminService.bindToken(command);
        return ResponseDTO.ok("ok");
    }

    @RequestMapping("/download")
    public ResponseDTO<List<BaiduPanFileMetaDTO>> download(@RequestBody DownloadCommand command) {
        List<BaiduPanFileMetaDTO> fileList = adminService.download(command);
        return ResponseDTO.ok(fileList);
    }
}
