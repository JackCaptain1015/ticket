package com.tqmall.ticket.biz.city;

import com.tqmall.ticket.biz.bo.TicketOpenCityBO;
import com.tqmall.ticket.biz.bo.TicketOpenCityListBO;
import com.tqmall.ticket.dal.entity.TicketOpenCity;

import java.util.List;
import java.util.Map;

/**
 * Created by wurenzhi on 2017/01/09.
 */
public interface OpenCityService {

    List<TicketOpenCityListBO> selectAllCity();

    List<TicketOpenCityBO> selectAreaByCityId(Integer cityId);
}
