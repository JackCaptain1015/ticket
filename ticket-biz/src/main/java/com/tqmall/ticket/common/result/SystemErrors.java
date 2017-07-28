package com.tqmall.ticket.common.result;

/**
 * Created by wurenzhi on 2016/12/26.
 */
public enum SystemErrors implements ServiceErrors {
    UNKNOWN("10001", "未知的严重错误类型。"),
    SIGN_FILED("10002", "invalid sign"),
    SERVER_BUSY("10003", "服务器繁忙，请稍后！");

    private final String code;
    private final String message;

    private SystemErrors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
