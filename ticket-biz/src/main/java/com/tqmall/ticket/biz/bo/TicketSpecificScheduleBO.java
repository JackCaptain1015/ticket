package com.tqmall.ticket.biz.bo;

import com.tqmall.ticket.dal.entity.TicketMovie;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Getter
@Setter
public class TicketSpecificScheduleBO {
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

    private List<String> existSeatList;

    private List<String> seatList;

    private TicketMovieBO ticketMovieBO;

    private TicketCinemaBO ticketCinemaBO;
}
