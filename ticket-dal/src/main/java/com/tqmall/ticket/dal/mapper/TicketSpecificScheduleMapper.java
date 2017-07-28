package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketSpecificSchedule;
import com.tqmall.ticket.dal.param.DSpecificScheduleParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TicketSpecificScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketSpecificSchedule record);

    int insertSelective(TicketSpecificSchedule record);

    TicketSpecificSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketSpecificSchedule record);

    int updateByPrimaryKey(TicketSpecificSchedule record);

    /**
     * 根据电影id和影院id搜索某天具体排片
     * @param param
     * @return
     */
    List<TicketSpecificSchedule> selectListByMovieIdAndCinemaId(DSpecificScheduleParam param);

}