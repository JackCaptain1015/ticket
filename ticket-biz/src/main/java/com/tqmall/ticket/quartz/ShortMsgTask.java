package com.tqmall.ticket.quartz;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.base.ShortMsgLogService;
import com.tqmall.ticket.biz.base.SmsService;
import com.tqmall.ticket.biz.bo.TicketShortMsgLogBO;
import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.common.JsonUtil;
import com.tqmall.ticket.common.constants.VerityCodeConstants;
import com.tqmall.ticket.common.msg.MobileMessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by wurenzhi on 2017/04/20.
 */
@Component
@Slf4j
public class ShortMsgTask {
    @Resource
    private ShortMsgLogService shortMsgLogService;

    // TODO: 2017/04/21 暂不考虑集群条件下
    @Scheduled(cron = "0 0/10 * * * ?")
    public void dealFailureMsg() throws IOException {
        List<TicketShortMsgLogBO> ticketShortMsgLogBOList = shortMsgLogService.selectAllFailueShortMsg();
        List<Integer> updateList = Lists.newArrayList();
        for (TicketShortMsgLogBO bo : ticketShortMsgLogBOList) {
            String ticketsNoListValue = JsonUtil.getJsonValue(bo.getMsgValue(), "ticketsNo");
            String value = MobileMessageSendUtil.moreParamDeal(ticketsNoListValue);
            Integer successInt = MobileMessageSendUtil.sendMsg(VerityCodeConstants.TICKET_CODE_TEMPLATE_ID,bo.getMsgMobile().toString(),value);
            if (successInt == 0){
                updateList.add(bo.getId());
            }
        }
        if (updateList.size() != 0){
            shortMsgLogService.batchUpdateDealMsg(updateList);
        }
    }

    @Scheduled(cron = "0 10 0 * * ?")
    public void dealDeadMsg(){
        shortMsgLogService.dealDeadMsg();
    }


}
