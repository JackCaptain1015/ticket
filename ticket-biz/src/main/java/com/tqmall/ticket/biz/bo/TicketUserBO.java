package com.tqmall.ticket.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/01/11.
 */
@Getter
@Setter
public class TicketUserBO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String password;

    private String userMobile;

    private Boolean isEnable;
}
