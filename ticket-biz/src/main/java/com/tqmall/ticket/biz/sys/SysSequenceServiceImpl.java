package com.tqmall.ticket.biz.sys;

import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.cache.sequence.SequenceCache;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.common.SimpleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wurenzhi on 2017/04/14.
 */
@Service
@Slf4j
public class SysSequenceServiceImpl implements SysSequenceService {

    private static final Lock OrderNoLock = new ReentrantLock(true);
    private static final Lock TicketNoLock = new ReentrantLock(true);

    @Resource
    private SequenceCache sequenceCache;

    @Override
    public String genOrderNo() {
        String dateStr = DateUtil.dateToStr(new Date(), DateUtil.yyMMdd);
        String key = String.format(RedisKeyBean.ORDER_NO_SEQ_KEY, dateStr);
        OrderNoLock.lock();
        try{
            int seq = sequenceCache.getSeqFromRedis(key, 1, RedisKeyBean.ONE_DAY)%10000;
            String no = dateStr + SimpleUtil.getRandomNum(4) + SimpleUtil.supplementNum(seq, 5);
            log.info("genOrderNo success, no:{}",no);
            return no;
        }catch (Exception e){
            String no = dateStr + "99999" + SimpleUtil.getRandomNum(4);
            log.error("genOrderNo error, no:{} errorMsg:{}",no, e.getMessage());
            return no;
        }finally {
            OrderNoLock.unlock();
        }
    }

    @Override
    public String genTicketNo() {
        String dateStr = DateUtil.dateToStr(new Date(), DateUtil.yyMMdd);
        String key = String.format(RedisKeyBean.TICKET_NO_KEY, dateStr);
        TicketNoLock.lock();
        try{
            /**电影票的位数要比订单位数至少大5倍，因为一张订单对多可以选5个座位*/
            int seq = sequenceCache.getSeqFromRedis(key, 1, RedisKeyBean.ONE_DAY)%100000;
            String no = dateStr + SimpleUtil.getRandomNum(4) + SimpleUtil.supplementNum(seq, 6);
            log.info("genTicketNo success, no:{}",no);
            return no;
        }catch (Exception e){
            String no = dateStr + "99999" + SimpleUtil.getRandomNum(5);
            log.error("genTicketNo error, no:{} errorMsg:{}",no, e.getMessage());
            return no;
        }finally {
            TicketNoLock.unlock();
        }
    }

}
