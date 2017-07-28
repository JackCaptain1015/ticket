package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketCinema;
import com.tqmall.ticket.dal.param.DCinemaParam;

import java.util.List;

public interface TicketCinemaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketCinema record);

    int insertSelective(TicketCinema record);

    TicketCinema selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketCinema record);

    int updateByPrimaryKey(TicketCinema record);

    /**
     * 根据cityId搜索影院
     * @param cityId
     * @return
     */
    List<TicketCinema> selectByCityId(Integer cityId);

    /**
     * 根据areaId搜索影院
     * @param areaId
     * @return
     */
    List<TicketCinema> selectByAreaId(Integer areaId);

    /**
     * 按条件搜索影院
     * @param dCinemaParam
     * @return
     */
    List<TicketCinema> searchByParam(DCinemaParam dCinemaParam);

}