package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Getter
@Setter
public class TicketMovieCinemaRelVO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private Integer movieId;

    private String movieName;

    private Integer cinemaId;

    private String cinemaName;

}
