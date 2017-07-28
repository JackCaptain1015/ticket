package com.tqmall.ticket.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by wurenzhi on 2017/04/22.
 */
public interface Callback {
    Object onTask(Jedis jedis);
}
