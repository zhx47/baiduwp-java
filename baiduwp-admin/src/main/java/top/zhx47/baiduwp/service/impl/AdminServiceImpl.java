package top.zhx47.baiduwp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanFileMetaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanQuotaDTO;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanXpanNasDTO;
import top.zhx47.baiduwp.common.AuthenticationUtils;
import top.zhx47.baiduwp.common.cache.SystemLoginUserCache;
import top.zhx47.baiduwp.common.config.JwtService;
import top.zhx47.baiduwp.common.service.BaiduOpenApiCustomerService;
import top.zhx47.baiduwp.common.service.BaiduPanCustomerService;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.controller.command.BindTokenCommand;
import top.zhx47.baiduwp.controller.command.DownloadCommand;
import top.zhx47.baiduwp.controller.command.LoginCommand;
import top.zhx47.baiduwp.controller.command.GetFileListCommand;
import top.zhx47.baiduwp.database.entity.TokenEntity;
import top.zhx47.baiduwp.common.exception.ApiException;
import top.zhx47.baiduwp.common.exception.error.ErrorCode;
import top.zhx47.baiduwp.database.entity.UserEntity;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;
import top.zhx47.baiduwp.database.service.TokenService;
import top.zhx47.baiduwp.database.service.UserService;
import top.zhx47.baiduwp.service.AdminService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SystemLoginUserCache systemLoginUserCache;
    private final TokenService tokenService;
    private final BaiduPanCustomerService baiduPanCustomerService;
    private final BaiduOpenApiCustomerService baiduOpenApiCustomerService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public SystemLoginUser getUserInfo() {
        SystemLoginUser systemLoginUser = AuthenticationUtils.getSystemLoginUser();
        if (systemLoginUser.getToken() == null) {
            TokenEntity token = tokenService.getById(systemLoginUser.getUsername());
            systemLoginUser.updateToken(token);
        }

        if (systemLoginUser.tokenIsValid() && systemLoginUser.getBaiduPanUserInfo() == null) {
            BaiduPanXpanNasDTO userInfo = baiduPanCustomerService.getUserInfo();
            systemLoginUser.updateBaiduPanUserInfo(userInfo);
        }
        systemLoginUserCache.put(systemLoginUser.getUsername(), systemLoginUser);
        return systemLoginUser;
    }

    @Override
    public BaiduPanQuotaDTO quota() {
        return baiduPanCustomerService.quota();
    }

    @Override
    public List<BaiduPanFileMetaDTO> getFileList(GetFileListCommand command) {
        if (command.getCategory() == null && !StringUtils.hasText(command.getKey())) {
            return baiduPanCustomerService.getFileList(command.getDir());
        } else if (command.getCategory() != null && !StringUtils.hasText(command.getKey())) {
            return baiduPanCustomerService.getCategoryFileList(command.getCategoryMapping(), command.getDir());
        }
        return baiduPanCustomerService.getFileListByKey(command.getCategoryMapping(), command.getDir(), command.getKey());
    }

    @Override
    public String login(LoginCommand command) {
        if ("register".equals(command.getOption())) {
            // 自动注册
            UserEntity user = new UserEntity();
            user.setUsername(command.getUsername());
            user.setPassword(passwordEncoder.encode(command.getPassword()));
            user.setStatus(YesOrNoEnum.NORMAL);
            userService.save(user);

            TokenEntity token = new TokenEntity();
            token.setUsername(command.getUsername());
            token.setAccountStatus(YesOrNoEnum.DISABLE);
            tokenService.save(token);
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            command.getUsername(),
                            command.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ApiException(ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.Business.LOGIN_ERROR, e.getMessage());
        }
        // 把当前登录用户 放入上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SystemLoginUser loginUser = (SystemLoginUser) authentication.getPrincipal();

        return jwtService.generateToken(loginUser);
    }

    @Override
    public void bindToken(BindTokenCommand command) {
        baiduOpenApiCustomerService.refreshAccessToken(command.getToken());
    }

    @Override
    public List<BaiduPanFileMetaDTO> download(DownloadCommand command) {
        return baiduPanCustomerService.download(command.getPath());
    }
}
