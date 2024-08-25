package top.zhx47.baiduwp.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zhx47.baiduwp.database.entity.UserEntity;
import top.zhx47.baiduwp.database.mapper.UserMapper;
import top.zhx47.baiduwp.database.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
