package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketUserLoginCode {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private Long userMobile;

    private Integer verifyCode;

    private Byte verifyStatus;

}