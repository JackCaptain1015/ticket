package com.tqmall.ticket.common.status;

import lombok.Getter;

/**
 * Created by wurenzhi on 2017/04/14.
 */
public enum OrderTypeEnum {
    DFK(0,"待付款"),
    YFK(1,"已付款"),
    YWC(2,"已完成"),
    YCS(3,"已超时");

    @Getter
    private Integer orderStatus;
    @Getter
    private String comment;

    OrderTypeEnum(Integer orderStatus, String comment) {
        this.orderStatus = orderStatus;
        this.comment = comment;
    }

}
