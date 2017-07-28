package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketShortMsgLog {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private Long msgMobile;

    private String msgValue;

    private Date msgDeadlineTime;

    private Integer msgStatus;

    private Long msgTemplateId;

    private Integer msgServiceProvider;

}