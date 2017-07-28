package com.tqmall.ticket.biz.base;

import com.google.common.collect.Lists;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.common.JsonUtil;
import com.tqmall.ticket.common.VerifyGenerator;
import com.tqmall.ticket.common.constants.VerityCodeConstants;
import com.tqmall.ticket.common.msg.MobileMessageSendUtil;
import com.tqmall.ticket.dal.entity.TicketShortMsgLog;
import com.tqmall.ticket.dal.entity.TicketUserLoginCode;
import com.tqmall.ticket.dal.mapper.TicketUserLoginCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/02/17.
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private TicketUserLoginCodeMapper ticketUserLoginCodeMapper;
    @Resource
    private ShortMsgLogService shortMsgLogService;

    @Override
    public boolean sendMessage(Long mobile) throws IOException {
        //发送之前判断最近是否发送过，如果发送过那么是否已经经过1分钟了
        TicketUserLoginCode beforeCode = ticketUserLoginCodeMapper.selectByMobile(mobile);
        Date now = new Date();
        if (beforeCode != null){
            // beforeCode存在并且距离上次发送短信还不足1分钟
            Date createDate = beforeCode.getGmtCreate();
            Date endDate = DateUtil.dateAdd(createDate, Calendar.MINUTE, 1);
            if ( now.before(endDate) ){
                return false;
            }
        }

        String verify = VerifyGenerator.genVerify();

        TicketUserLoginCode code = new TicketUserLoginCode();
        code.setGmtCreate(now);
        code.setGmtModified(now);
        code.setUserMobile(mobile);
        code.setVerifyCode(Integer.parseInt(verify));
        code.setVerifyStatus(VerityCodeConstants.VERIFY_STATUS_INIT);

        //发送验证码
//        Integer successInt = MobileMessageSendUtil.sendMsg(VerityCodeConstants.VERITY_CODE_TEMPLATE_ID,mobile.toString(),verify);
        Integer successInt = 0;
        int insertSuccess = 0;
        if (successInt == 0){
            //将验证码插入数据库
            insertSuccess = ticketUserLoginCodeMapper.insertSelective(code);
        }

        return insertSuccess > 0;
    }

    @Override
    public boolean checkVerifyCode(Long mobile,Integer verifyCode) {

        //可能在不同时刻发送了相同的验证码，所以查出多条
        List<TicketUserLoginCode> ticketUserLoginCodeList = ticketUserLoginCodeMapper.selectByMobileAndVerifyCode(mobile, verifyCode);
        List<TicketUserLoginCode> dealFailList = Lists.newArrayList();
        List<TicketUserLoginCode> dealSuccessList = Lists.newArrayList();
        Date now = new Date();
        boolean checkFlag = false;
        //List数据很小，至多不会超过10条(因为至少隔一分钟后才能再次发送短信)
        for (TicketUserLoginCode code:ticketUserLoginCodeList) {
            /**检查验证码的时效性*/
            Date gmtCreate = code.getGmtCreate();
            Date endDate = DateUtil.dateAdd(gmtCreate, Calendar.MINUTE, 10);
            if (now.before(endDate)){
                checkFlag = true;
                dealSuccessList.add(code);
            }else{
                dealFailList.add(code);
            }
        }
        if (dealFailList.size() > 0){
            ticketUserLoginCodeMapper.batchUpdateFailByIdList(dealFailList);
        }
        if (dealSuccessList.size() > 0){
            ticketUserLoginCodeMapper.batchUpdateSuccessByIdList(dealSuccessList);
        }
        return checkFlag;
    }

    @Override
    public boolean ticketsNo(Long mobile, String ticketsNo, Date movieShowTime) throws IOException {
        String value = MobileMessageSendUtil.moreParamDeal(ticketsNo);
//        Integer successInt = MobileMessageSendUtil.sendMsg(VerityCodeConstants.TICKET_CODE_TEMPLATE_ID,mobile.toString(),value);
        Integer successInt = 0;
        /**短信发送失败插入流水表中*/
        if (successInt != 0){
            TicketShortMsgLog log = new TicketShortMsgLog();
            log.setMsgMobile(mobile);
            log.setMsgValue(JsonUtil.keyValueToJsonStr("ticketsNo",ticketsNo));
            log.setMsgDeadlineTime(movieShowTime);
            log.setMsgTemplateId(Long.parseLong(VerityCodeConstants.TICKET_CODE_TEMPLATE_ID));
            int i = shortMsgLogService.insertSelective(log);
        }
        /**成功的时候返回0*/
        return successInt == 0;
    }
}
