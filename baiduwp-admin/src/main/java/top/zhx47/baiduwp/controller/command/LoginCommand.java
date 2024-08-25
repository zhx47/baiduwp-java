package top.zhx47.baiduwp.controller.command;

import lombok.Data;

@Data
public class LoginCommand {
    private String username;
    private String password;
    private String option;
}
