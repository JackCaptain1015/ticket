package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketShowroom;

public interface TicketShowroomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketShowroom record);

    int insertSelective(TicketShowroom record);

    TicketShowroom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketShowroom record);

    int updateByPrimaryKey(TicketShowroom record);
}