package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketOpenCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketOpenCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketOpenCity record);

    int insertSelective(TicketOpenCity record);

    TicketOpenCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketOpenCity record);

    int updateByPrimaryKey(TicketOpenCity record);

    List<TicketOpenCity> selectAllCity();

    List<TicketOpenCity> selectAreaByCityId(@Param("cityId") Integer cityId);


}