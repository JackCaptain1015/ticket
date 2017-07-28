package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketShortMsgLog;

import java.util.List;

public interface TicketShortMsgLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketShortMsgLog record);

    int insertSelective(TicketShortMsgLog record);

    TicketShortMsgLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketShortMsgLog record);

    int updateByPrimaryKey(TicketShortMsgLog record);

    List<TicketShortMsgLog> selectAllFailueShortMsg();

    int batchUpdateDealMsg(List<Integer> list);

    int dealDeadMsg();
}