package com.tqmall.ticket.web.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/03/03.
 */
@Getter
@Setter
public class SpecificScheduleParam {
    private Integer movieId;
    private Integer cinemaId;
    //某一天，如2017-03-02
    private String day;
}
