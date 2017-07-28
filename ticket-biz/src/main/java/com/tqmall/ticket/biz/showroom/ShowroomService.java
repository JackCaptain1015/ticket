package com.tqmall.ticket.biz.showroom;

import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.dal.entity.TicketShowroom;

/**
 * Created by wurenzhi on 2017/03/09.
 */
public interface ShowroomService {

    TicketShowroomBO selectByPrimaryKey(Integer id);
}
