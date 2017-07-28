package com.tqmall.ticket.biz.sys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 2017/04/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-biz-context.xml")
public class SysSequenceServiceTest {
    @Resource
    private SysSequenceService sysSequenceService;

    @Test
    public void genOrderNoTest(){
        String orderNo = sysSequenceService.genOrderNo();
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
        System.out.println(orderNo);
    }
}
