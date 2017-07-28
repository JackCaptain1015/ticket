package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TicketCinema {
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