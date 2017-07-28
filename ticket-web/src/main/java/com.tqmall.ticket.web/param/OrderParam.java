package com.tqmall.ticket.web.param;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by wurenzhi on 2017/04/14.
 */
@Getter
@Setter
public class OrderParam extends PageParam{

    private Integer scheduleId;

    private String orderSeat;

    /**接收短信的手机号*/
    private Long orderMobile;

}
