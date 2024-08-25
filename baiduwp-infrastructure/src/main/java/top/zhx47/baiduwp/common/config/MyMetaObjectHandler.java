package top.zhx47.baiduwp.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.zhx47.baiduwp.common.AuthenticationUtils;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createUser", String.class, getUserIdSafely());

        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateUser", String.class, getUserIdSafely());

        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    public String getUserIdSafely() {
        String username = "system";
        try {
            username = AuthenticationUtils.getUsername();
        } catch (Exception e) {
            log.warn("can not find user in current thread.");
        }
        return username;
    }
}