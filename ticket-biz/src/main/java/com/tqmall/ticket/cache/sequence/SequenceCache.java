package com.tqmall.ticket.cache.sequence;

import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisCacheIndex;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 16/6/5.
 */
@Component
public class SequenceCache {
    @Resource
    private JedisClient jedisClient;

    /**
     * 从redis获取自增序列
     * @param key
     * @param defaultValue
     * @param expire
     * @return
     */
    public int getSeqFromRedis(String key, int defaultValue, int expire){
        Integer seq = jedisClient.get(RedisCacheIndex.UN_FLUSH_DB, key, Integer.class);
        if(seq==null){
            jedisClient.set(RedisCacheIndex.UN_FLUSH_DB, key, expire, defaultValue);
            return defaultValue;
        }
        jedisClient.incr(RedisCacheIndex.UN_FLUSH_DB, key);
        return seq + 1;
    }

}
