package com.tqmall.ticket.biz.cinema;

import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.param.BCinemaParam;
import com.tqmall.ticket.dal.entity.TicketCinema;
import com.tqmall.ticket.dal.param.DCinemaParam;

import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */
public interface CinemaService {

    int insertSelective(TicketCinema record);

    /**
     * 根据cityId搜索影院
     * @param cityId
     * @return
     */
    List<TicketCinemaBO> selectByCityId(Integer cityId);

    /**
     * 根据areaId搜索影院
     * @param areaId
     * @return
     */
    List<TicketCinemaBO> selectByAreaId(Integer areaId);

    /**
     * 按条件搜索影院
     * @param dCinemaParam
     * @return
     */
    List<TicketCinemaBO> searchByParam(BCinemaParam bCinemaParam);

    TicketCinemaBO selectByPrimaryKey(Integer id);
}
