package com.tqmall.ticket.biz.sys;

/**
 * Created by wurenzhi on 2017/04/14.
 */
public interface SysSequenceService {
    //生成工单 NO 方法
    String genOrderNo();

    //生成电影票编号
    String genTicketNo();

}
