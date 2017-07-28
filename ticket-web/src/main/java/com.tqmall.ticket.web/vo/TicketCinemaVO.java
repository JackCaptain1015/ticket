package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Getter
@Setter
public class TicketCinemaVO {
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

    /**影院门店图片url*/
    private String cinemaPicture;

}
