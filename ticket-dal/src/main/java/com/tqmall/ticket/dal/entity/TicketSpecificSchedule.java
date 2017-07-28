package com.tqmall.ticket.dal.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TicketSpecificSchedule {
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

    private Date movieShowTime;

    private Date movieEndTime;

    private String movieLanguageVersion;

    private Integer showroomId;

    private String showroomName;

    private String existSeatTable;

    private BigDecimal presentPrice;

    private BigDecimal cinemaPrice;

}