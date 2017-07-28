package com.tqmall.ticket.cache;

/**
 * redis key 统一放置管理
 * Created by wurenzhi
 */
public class RedisKeyBean {
    //系统前缀
    public static final String SYSTEM_PRE = "ticket_";

    //过期时间
    public static final int FOREVER = 0;
    public static final int ONE_HOUR = 3600;
    public static final int ONE_DAY = 24*3600;
    public static final int FIVE_MINUTE = 300;
    public static final int TWO_MINUTE = 120;
    public static final int HALF_MINUTE = 30;
    public static final int FIFTEEN_MINUTE = 15*60;


    //订单编号每日序列
    public static final String ORDER_NO_SEQ_KEY = SYSTEM_PRE + "order_no_seq_%s";

    //电影票编号
    public static final String TICKET_NO_KEY = SYSTEM_PRE + "order_tickets_no_%s";

    //根据手机号缓存验证码
    public static final String VERIFY_CODE_KEY = SYSTEM_PRE + "verify_code_%s";

    //获取登录频度
    public static final String LOGIN_FREQUENCY_KEY = SYSTEM_PRE + "login_frequency_%s";

    //电影详细缓存
    public static final String MOVIE_DETAIL_KEY = SYSTEM_PRE + "movie_detail_%s";

    //影院详细缓存
    public static final String CINEMA_DETAIL_KEY = SYSTEM_PRE + "cinema_detail_%s";

    //影院城市列表缓存
    public static final String CINEMA_CITY_KEY = SYSTEM_PRE + "cinema_city_%s";

    //影院区域列表缓存
    public static final String CINEMA_AREA_KEY = SYSTEM_PRE + "cinema_area_%s";


}
