package com.tqmall.ticket.biz.movie;

import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.param.BSpecificScheduleParam;
import com.tqmall.ticket.dal.entity.TicketSpecificSchedule;
import com.tqmall.ticket.dal.param.DSpecificScheduleParam;

import java.util.List;

/**
 * Created by wurenzhi on 2017/03/02.
 */
public interface SpecificScheduleService {
    /**
     * 根据电影id和影院id搜索某天具体排片
     * @param param
     * @return
     */
    List<TicketSpecificScheduleBO> selectListByMovieIdAndCinemaId(BSpecificScheduleParam param);

    TicketSpecificScheduleBO selectByPrimaryKey(Integer id);

    int updateBySelective(TicketSpecificSchedule record);

}
