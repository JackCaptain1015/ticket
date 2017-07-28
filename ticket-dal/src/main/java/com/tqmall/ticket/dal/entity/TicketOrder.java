package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TicketOrder {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    /**订单编号*/
    private String orderNo;

    /**影片开始播放时间*/
    private Date movieShowTime;

    private Integer scheduleId;

    private Integer userId;

    private String orderSeat;

    /**票数*/
    private Integer orderTickets;

    /**生成的电影票取货码*/
    private String orderTicketsNo;

    /**线上购买总格（当前价）*/
    private BigDecimal orderPresentPrice;

    /**线下购买总格（影院价）*/
    private BigDecimal orderCinemaPrice;

    /**优惠金额（总共）*/
    private BigDecimal orderPreferentialPrice;

    /**接收短信的手机号*/
    private Long orderMobile;

    /**订单状态 0：待付款 1：已付款 2：已完成 3：已退单*/
    private Integer orderStatus;

    public BigDecimal getOrderPreferentialPrice(){
        return orderCinemaPrice.subtract(orderPresentPrice);
    }

}