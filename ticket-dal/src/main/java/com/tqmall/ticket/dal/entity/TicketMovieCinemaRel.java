package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class TicketMovieCinemaRel {
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
    //影院播放日期
    private Date movieEffectiveDate;

    //影院下线日期
    private Date movieExpirationDate;

}