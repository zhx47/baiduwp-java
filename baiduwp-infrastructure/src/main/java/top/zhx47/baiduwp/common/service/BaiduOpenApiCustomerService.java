package top.zhx47.baiduwp.common.service;

import top.zhx47.baiduwp.api.baidu.openapi.dto.BaiduOpenApiTokenDTO;

public interface BaiduOpenApiCustomerService {

    void refreshAccessToken(String refreshToken);
}
