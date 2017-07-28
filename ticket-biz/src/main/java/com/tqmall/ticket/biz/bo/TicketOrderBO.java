package com.tqmall.ticket.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/04/10.
 */
@Getter
@Setter
public class TicketOrderBO {
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

    private BigDecimal orderPresentPrice;

    private BigDecimal orderCinemaPrice;

    private BigDecimal orderPreferentialPrice;

    /**接收短信的手机号*/
    private Long orderMobile;

    /**订单状态 0：待付款 1：已付款 2：已完成 3：已退单*/
    private Integer orderStatus;

    /**实体类放下面*/
    private TicketSpecificScheduleBO ticketSpecificScheduleBO;

    private TicketMovieBO ticketMovieBO;

    private TicketCinemaBO ticketCinemaBO;
}
