package top.zhx47.baiduwp.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zhx47.baiduwp.database.entity.TokenEntity;
import top.zhx47.baiduwp.database.mapper.TokenMapper;
import top.zhx47.baiduwp.database.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl extends ServiceImpl<TokenMapper, TokenEntity> implements TokenService  {

}
