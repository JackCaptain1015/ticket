package com.tqmall.ticket.biz.authorization;

import com.tqmall.ticket.biz.base.SmsService;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.constants.TicketConstants;
import com.tqmall.ticket.common.constants.VerityCodeConstants;
import com.tqmall.ticket.dal.entity.TicketUserLoginCode;
import com.tqmall.ticket.dal.mapper.TicketUserLoginCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/01/12.
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    private TicketUserLoginCodeMapper ticketUserLoginCodeMapper;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private SmsService smsService;

    private boolean check(String mobile, String code, TicketUserLoginCode record){
        String key = String.format(RedisKeyBean.VERIFY_CODE_KEY, mobile);
        if(code.equals(record.getVerifyCode())){
            record.setGmtModified(new Date());
            record.setVerifyStatus(VerityCodeConstants.VERIFY_STATUS_SUCCESS);
            ticketUserLoginCodeMapper.updateByPrimaryKeySelective(record);
            jedisClient.delete(key);
            return true;
        }
        String vc = jedisClient.get(key, String.class);
        if(vc==null){
            record.setGmtModified(new Date());
            record.setVerifyStatus(VerityCodeConstants.VERIFY_STATUS_FAILED);
            ticketUserLoginCodeMapper.updateByPrimaryKeySelective(record);
        }
        return false;
    }

    @Override
    public boolean verifying(Long mobile, Integer code) {
        return smsService.checkVerifyCode(mobile,code);
    }

    @Override
    public boolean addVerifyRecord(String mobile, String code) {
        return false;
    }

    @Override
    public int dealDeadVerifyCode() {
        return ticketUserLoginCodeMapper.dealDeadVerifyCode();
    }
}
