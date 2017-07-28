package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketOrder;
import com.tqmall.ticket.dal.param.DOrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketOrder record);

    int insertSelective(TicketOrder record);

    TicketOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketOrder record);

    int updateByPrimaryKey(TicketOrder record);

    List<TicketOrder> selectAllByUserId(DOrderParam dOrderParam);

    List<TicketOrder> selectLockSeatByScheduleId(Integer scheduleId);

    int dealDeadOrder(String orderNo);

    int getWaitPayOrderCount(Integer userId);

    List<TicketOrder> selectAllWaitPayByUserId(DOrderParam dOrderParam);


}