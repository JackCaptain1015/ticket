package com.tqmall.ticket.common.constants;

/**
 * 业务常量
 * Created by wurenzhi on 2017/01/11.
 */
public interface TicketConstants {
    //默认分页条数
    final int DEFAULT_PAGE_SIZE = 10;

    /**
     *  SESSION缓存：当前用户信息
     */
    String SESSION_KEY_CN_USER_INFO = "TICKET_CURRENT_USER_INFO_";

    /**
     * 验证码开关
     */
    String VERIFY_ON = "on"; //开启验证码

    /**
     * 相关url
     */
    final String AUTHORIZATION_MAIN_PAGE = "/page/index";//主页面

}
