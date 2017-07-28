package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2017/02/23.
 */
@Getter
@Setter
public class LoginInfoVO {
    private String verifySwitch;
    private String mobile;
    private String message;
    private String checkCode;
    private String pageUrl;
}
