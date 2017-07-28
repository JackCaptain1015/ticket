package com.tqmall.ticket.biz.base;

import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.entity.TicketLoginLog;
import com.tqmall.ticket.dal.mapper.TicketLoginLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/04/21.
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private TicketLoginLogMapper ticketLoginLogMapper;
    @Resource
    private JedisClient jedisClient;

    @Override
    public int insertSelective(Long mobile) {
        TicketLoginLog log = new TicketLoginLog();
        Date now = new Date();
        log.setGmtCreate(now);
        log.setGmtModified(now);
        log.setMobile(mobile);
        return ticketLoginLogMapper.insertSelective(log);
    }

    @Override
    public boolean isTooFrequent(Long mobile) {
        String key = String.format(RedisKeyBean.LOGIN_FREQUENCY_KEY , mobile);
        /**如果该手机号还在禁止访问期间，直接返回true*/
        Long getMobile = jedisClient.get(key,Long.class);
        if (getMobile != null){
            return true;
        }
        /**查询1分钟内请求登录的次数*/
        Date now = new Date();
        Date nowAfterOneMin = DateUtil.afterTime(Calendar.MINUTE, 1);
        String nowString = DateUtil.date2Hms(now);
        String nowAfterOneMinString = DateUtil.date2Hms(nowAfterOneMin);
        int count = ticketLoginLogMapper.selectLoginCountByMobile(mobile,nowString,nowAfterOneMinString);
        /**请求大于等于3次则认为太频繁
         * 加入redis，让该手机号2分钟后再试*/
        if (count >= 3){
            jedisClient.set(key,120,mobile);
            return true;
        }
        return false;
    }


}
