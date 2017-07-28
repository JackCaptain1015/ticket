package com.tqmall.ticket.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Getter
@Setter
public class TicketCinemaBO implements Serializable{
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String cinemaName;

    private Integer cinemaCityId;

    private String cinemaCityName;

    private Integer cinemaAreaId;

    private String cinemaAreaName;

    private String cinemaAddress;

    private String cinemaMobile;

    private String cinemaPicture;
}
