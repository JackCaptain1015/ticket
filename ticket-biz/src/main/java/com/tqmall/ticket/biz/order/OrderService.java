package com.tqmall.ticket.biz.order;

import com.tqmall.ticket.biz.bo.TicketOrderBO;
import com.tqmall.ticket.biz.param.BOrderParam;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.dal.entity.TicketOrder;
import com.tqmall.ticket.dal.param.DOrderParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by wurenzhi on 2017/04/10.
 */
public interface OrderService {
    Result insertSelective(TicketOrder record);

    TicketOrderBO selectById(Integer orderId);

    int update(TicketOrder record) throws IOException;

    Result selectAllOrder(BOrderParam bOrderParam);

    List<String> selectLockSeatByScheduleId(Integer scheduleId);

    int dealDeadOrder(String orderNo);

    int getWaitPayOrderCount();

    Result selectAllWaitPayByUserId(BOrderParam bOrderParam);
}
