package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/04/09.
 */
@Getter
@Setter
public class TicketOpenCityVO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private Integer cityId;

    private String cityName;

    private Integer areaId;

    private String areaName;

    //城市名字的拼音
    private String spellCityName;
}
