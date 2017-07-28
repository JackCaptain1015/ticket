package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketLoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface TicketLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketLoginLog record);

    int insertSelective(TicketLoginLog record);

    TicketLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketLoginLog record);

    int updateByPrimaryKey(TicketLoginLog record);

    int selectLoginCountByMobile(@Param("mobile") Long mobile,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate);
}