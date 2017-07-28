package com.tqmall.ticket.biz.base;

import com.tqmall.ticket.biz.bo.TicketShortMsgLogBO;
import com.tqmall.ticket.dal.entity.TicketShortMsgLog;

import java.util.List;

/**
 * Created by wurenzhi on 2017/04/16.
 */
public interface ShortMsgLogService {
    int insertSelective(TicketShortMsgLog record);

    List<TicketShortMsgLogBO> selectAllFailueShortMsg();

    int batchUpdateDealMsg(List<Integer> list);

    int dealDeadMsg();

}
