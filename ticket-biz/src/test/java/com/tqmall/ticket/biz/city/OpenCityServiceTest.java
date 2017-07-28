package com.tqmall.ticket.biz.city;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.base.LoginLogService;
import com.tqmall.ticket.biz.base.SmsService;
import com.tqmall.ticket.biz.base.SmsServiceImpl;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.bo.TicketOpenCityBO;
import com.tqmall.ticket.biz.bo.TicketOpenCityListBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.rpc.TicketHelloService;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.JedisObjectClient;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.mapper.TicketLoginLogMapper;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-biz-context.xml")
public class OpenCityServiceTest {
    @Resource
    private OpenCityService openCityService;
    @Resource
    private TicketHelloService ticketHelloService;
    @Resource
    private SmsService smsService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private JedisObjectClient jedisObjectClient;
    @Resource
    private JedisClient jedisClient;

    @Test
    public void helloTest() throws IOException {
//        List<Integer> integerList = Lists.newArrayList();
//        integerList.add(1);
//        integerList.add(2);
        String key = "存的中文";
//        jedisClient.pushList(key,60*60,integerList);
//        List<Integer> listAllRange = jedisClient.getListAllRange(key, Integer.class);
//        System.out.println(listAllRange);
//        jedisClient.trimAllList(key);
//        List<Integer> listAllRange2 = jedisClient.getListAllRange(key, Integer.class);
//        System.out.println(listAllRange2);
    }
}
