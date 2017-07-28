package com.tqmall.ticket.biz.showroom;

import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.dal.entity.TicketShowroom;
import com.tqmall.ticket.dal.mapper.TicketShowroomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 2017/03/09.
 */
@Service
public class ShowroomServiceImpl implements ShowroomService {

    @Resource
    private TicketShowroomMapper ticketShowroomMapper;


    @Override
    public TicketShowroomBO selectByPrimaryKey(Integer id) {
        TicketShowroom ticketShowroom = ticketShowroomMapper.selectByPrimaryKey(id);
        TicketShowroomBO ticketShowroomBO = BdUtil.do2bo(ticketShowroom, TicketShowroomBO.class);
        return ticketShowroomBO;
    }
}
