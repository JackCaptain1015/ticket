package com.tqmall.ticket.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/04/20.
 */
@Getter
@Setter
public class TicketShortMsgLogBO {
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
