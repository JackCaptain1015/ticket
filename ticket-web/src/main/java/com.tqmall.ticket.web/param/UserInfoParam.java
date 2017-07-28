package com.tqmall.ticket.web.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2017/01/12.
 */
@Getter
@Setter
public class UserInfoParam {
    private String password;
    private String userMobile;
    //短信验证码
    private String checkCode;
    //验证码开关
    private String verifySwitch;
}
