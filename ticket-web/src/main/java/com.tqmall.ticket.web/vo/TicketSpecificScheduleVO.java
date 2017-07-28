package com.tqmall.ticket.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/03/03.
 */
@Getter
@Setter
public class TicketSpecificScheduleVO {
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

    private TicketMovieVO ticketMovieVO;

    private TicketCinemaVO ticketCinemaVO;

    private Long movieShowTimeMill;

    public Long getMovieShowTimeMill(){
        return movieShowTime.getTime();
    }

}
