package com.tqmall.ticket.dal.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Getter
@Setter
public class DSpecificScheduleParam {
    private Integer movieId;
    private Integer cinemaId;
    //开始时间
    private Date beginTime;
    //结束时间
    private Date endTime;
}
