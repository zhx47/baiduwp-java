package top.zhx47.baiduwp.common.exception.error;

import org.springframework.util.Assert;

/**
 * 错误码集合
 *
 * @author valarchie
 */
public enum ErrorCode implements ErrorCodeInterface {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败");

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }


    public enum Business implements ErrorCodeInterface {
        USER_NON_EXIST(10501, "登录用户不存在"),
        USER_IS_DISABLE(10502, "账号已停用"),
        LOGIN_WRONG_USER_PASSWORD(10201, "用户密码错误，请重输"),
        LOGIN_ERROR(10201, "登录失败"),
        BAIDUPAN_EXPIRE(10503, "绑定的百度网盘账号已过期"),
        BAIDUPAN_AUTO_REFRESH(10503, "绑定的百度网盘账号自动刷新token，请重试");

        private final int code;
        private final String msg;


        Business(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }
}