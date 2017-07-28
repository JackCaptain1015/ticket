package com.tqmall.ticket.biz.base;

import com.tqmall.ticket.biz.bo.TicketShortMsgLogBO;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.dal.entity.TicketShortMsgLog;
import com.tqmall.ticket.dal.mapper.TicketShortMsgLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/04/16.
 */
@Service
public class ShortMsgLogServiceImpl implements ShortMsgLogService {

    @Resource
    private TicketShortMsgLogMapper ticketShortMsgLogMapper;

    @Override
    public int insertSelective(TicketShortMsgLog record) {
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        record.setMsgStatus(0);
        /**默认使用网易云信*/
        record.setMsgServiceProvider(1);

        return ticketShortMsgLogMapper.insertSelective(record);
    }

    @Override
    public List<TicketShortMsgLogBO> selectAllFailueShortMsg() {
        List<TicketShortMsgLog> ticketShortMsgLogList = ticketShortMsgLogMapper.selectAllFailueShortMsg();
        List<TicketShortMsgLogBO> ticketShortMsgLogBOList = BdUtil.bo2do4List(ticketShortMsgLogList, TicketShortMsgLogBO.class);
        return ticketShortMsgLogBOList;
    }

    @Override
    public int batchUpdateDealMsg(List<Integer> list) {
        return ticketShortMsgLogMapper.batchUpdateDealMsg(list);
    }

    @Override
    public int dealDeadMsg() {
        return ticketShortMsgLogMapper.dealDeadMsg();
    }
}
