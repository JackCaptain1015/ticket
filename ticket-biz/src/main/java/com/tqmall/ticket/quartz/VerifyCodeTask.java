package com.tqmall.ticket.quartz;

import com.tqmall.ticket.biz.authorization.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 2017/04/22.
 */
@Component
@Slf4j
public class VerifyCodeTask {
    @Resource
    private VerifyService verifyService;

    @Scheduled(cron = "0 0 * * * ?")
    public void dealDeadVerifyCode(){
        verifyService.dealDeadVerifyCode();
    }
}
