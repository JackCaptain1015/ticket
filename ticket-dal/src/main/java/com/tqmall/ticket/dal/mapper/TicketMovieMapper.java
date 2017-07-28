package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketMovie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMovieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketMovie record);

    int insertSelective(TicketMovie record);

    TicketMovie selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketMovie record);

    int updateByPrimaryKey(TicketMovie record);

    /**
     * 正在热映
     * @return
     */
    List<TicketMovie> selectAllShowingMovie(@Param("pageSize") Integer pageSize);

    /**
     * 即将热映
     * @return
     */
    List<TicketMovie> selectReadyShowingMovie(@Param("pageSize") Integer pageSize);
}