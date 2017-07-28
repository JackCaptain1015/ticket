package com.tqmall.ticket.error;

/**
 * Created by wurenzhi on 2016/12/27.
 */
public class TicketErrorCode {

    public static final ErrorCode UNKNOW_ERROR = new ErrorCode("0000","未知错误");

    public static final ErrorCode PARAM_ERROR = new ErrorCode("0001","参数错误");

    //账号错误码
    public static final ErrorCode ACCOUNT_NOT_ENABLE = new ErrorCode("0002","账户为开通");

    public static final ErrorCode LOGIN_SYS_ERROR = new ErrorCode("0003","账号或验证码错误");

    public static final ErrorCode LOGIN_CODE_EMPTY = new ErrorCode("0004","验证码不得为空");

    public static final ErrorCode MSG_SENDING_ERROR = new ErrorCode("0005","验证码发送失败");

    public static final ErrorCode USER_AUTH_ERROR = new ErrorCode("0006","用户未登录");

    public static final ErrorCode LOGIN_TOO_FREQUENT = new ErrorCode("0007","登录过于频繁");


    //订单错误码
    public static final ErrorCode ORDER_SEAT_EXIST_ERROR = new ErrorCode("1001","座位已存在，请刷新页面重新下单");

    public static final ErrorCode ORDER_SAVE_ERROR = new ErrorCode("1002","因未知错误，订单保存失败，请刷新页面重新下单");



    //电影错误码

    //影院错误码
}
