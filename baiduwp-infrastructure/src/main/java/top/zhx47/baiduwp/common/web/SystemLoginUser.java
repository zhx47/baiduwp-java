package top.zhx47.baiduwp.common.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.zhx47.baiduwp.api.baidu.pan.dto.BaiduPanXpanNasDTO;
import top.zhx47.baiduwp.database.entity.TokenEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SystemLoginUser implements UserDetails {
    private String username;
    private String password;
    private TokenInfo token;
    private BaiduPanUserInfo baiduPanUserInfo;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public SystemLoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    public TokenInfo getToken() {
        return token;
    }

    public boolean tokenIsValid() {
        if (token == null || token.getExpiresIn() == null || token.getExpiresIn().isBefore(LocalDateTime.now())) {
            token = null;
        }
        return token != null;
    }

    public void updateToken(TokenEntity token) {
        this.token = new TokenInfo(token);
    }

    public void updateBaiduPanUserInfo(BaiduPanXpanNasDTO userInfo) {
        this.baiduPanUserInfo = new BaiduPanUserInfo(userInfo.getBaiduName(), userInfo.getAvatarUrl());
    }
}
