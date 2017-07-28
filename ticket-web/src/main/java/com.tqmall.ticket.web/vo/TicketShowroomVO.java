package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/03/09.
 */
@Getter
@Setter
public class TicketShowroomVO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String showroomName;

    private String seatTable;

    private Integer cinemaId;
}
