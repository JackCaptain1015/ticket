package com.tqmall.ticket.biz.base;

import com.tqmall.ticket.dal.entity.TicketLoginLog;

/**
 * Created by wurenzhi on 2017/04/21.
 */
public interface LoginLogService {
    public int insertSelective(Long mobile);

    /**
     * 查询该手机号一分钟之内登录的次数
     * */
    boolean isTooFrequent(Long mobile);
}
