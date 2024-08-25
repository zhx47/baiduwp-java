package top.zhx47.baiduwp.database.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;

import java.time.LocalDateTime;

@Data
@TableName("baiduwp_token")
@EqualsAndHashCode(callSuper = true)
public class TokenEntity extends BaseEntity {

    @TableId
    public String username;

    private YesOrNoEnum accountStatus;
    private String refreshToken;
    private String accessToken;
    private LocalDateTime expiresIn;

}
