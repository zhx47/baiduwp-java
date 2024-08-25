package top.zhx47.baiduwp.common.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.zhx47.baiduwp.common.cache.SystemLoginUserCache;
import top.zhx47.baiduwp.common.exception.ApiException;
import top.zhx47.baiduwp.common.web.SystemLoginUser;
import top.zhx47.baiduwp.database.entity.UserEntity;
import top.zhx47.baiduwp.common.exception.error.ErrorCode;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;
import top.zhx47.baiduwp.database.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final SystemLoginUserCache systemLoginUserCache;

    @Override
    public SystemLoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getById(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ApiException(ErrorCode.Business.USER_NON_EXIST);
        }
        if (YesOrNoEnum.NORMAL != user.getStatus()) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ApiException(ErrorCode.Business.USER_IS_DISABLE);
        }

        SystemLoginUser systemLoginUser = new SystemLoginUser(user.getUsername(), user.getPassword());
        systemLoginUserCache.put(username, systemLoginUser);
        return systemLoginUser;
    }
}
