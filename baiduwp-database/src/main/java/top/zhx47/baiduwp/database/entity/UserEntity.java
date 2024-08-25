package top.zhx47.baiduwp.database.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.zhx47.baiduwp.database.enums.YesOrNoEnum;

@Data
@TableName("baiduwp_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @TableId
    private String username;

    private String password;

    private YesOrNoEnum status;

}
