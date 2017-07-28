package com.tqmall.ticket.biz.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Getter
@Setter
public class BSpecificScheduleParam {
    private Integer movieId;
    private Integer cinemaId;
    //某一天，如2017-03-02
    private String day;
}
