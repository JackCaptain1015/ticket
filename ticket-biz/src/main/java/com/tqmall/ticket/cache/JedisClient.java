package com.tqmall.ticket.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JedisClient
 * redis原子操作封装，该bean作为单例对象
 * Created by wurenzhi
 */
@Slf4j
@Component
public class JedisClient {
    /**
     * 冲突延时 1000ms
     */
    private static final int MUTEX_EXP = 1000;
    

    @Resource
    private JedisPool jedisPool;
    @Resource
    private JedisUtil jedisUtil;



    /**
     * 清空整个列表
     */
    public boolean trimAllList(final int dbIndex, final String key){
        /**
         * ltrim的作用是保留下start到end的值，所以如果start>end,那么全部被清空
         * */
        return this.trimList(dbIndex,key,1L,0L);
    }

    public boolean trimAllList(final String key){
        return this.trimList(RedisCacheIndex.DEFAULT_DB,key,1L,0L);
    }

    public Boolean trimList(final String key, final Long start, final Long end) {
        return this.trimList(RedisCacheIndex.DEFAULT_DB, key, start, end);
    }

    /***
     * 清空start到end的值（包含两端）
     * key是否进行SafeEncoder无所谓，因为调用Jedis（子类）的时候它会自己
     * 调用一遍SafeEncoder，然后再调用BinaryJedis（父类），这里自己调用
     * SafeEncoder那是直接调用了BinaryJedis
     */
    public Boolean trimList(final int dbIndex, final String key, final Long start, final Long end) {
        if (key == null || start == null || end == null) {
            return Boolean.FALSE;
        }
        Object succeed = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                String ret;
                byte[] bKey = SafeEncoder.encode(key);
                ret = jedis.ltrim(bKey, start, end);
                return "OK".equals(ret);
            }
        });
        return succeed != null && (boolean) succeed;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String key, final Class<T> cls) {
        return this.get(RedisCacheIndex.DEFAULT_DB, key, cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final int dbIndex, final String key, final Class<T> cls) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                T obj = null;

                if (jedisUtil.isSimpleObj(cls)) {
                    String str = jedis.get(key);
                    if (str != null)
                        obj = jedisUtil.createSimpleObj(str, cls);
                } else {
                    RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(cls);
                    byte[] bs = jedis.get(SafeEncoder.encode(key));
                    if (bs != null) {
                        obj = runtimeSchema.newMessage();
                        ProtostuffIOUtil.mergeFrom(bs,obj,runtimeSchema);
                    }
                }

                return obj;
            }
        });
        return ret == null ? null : (T) ret;
    }

    public <T> boolean set(final String key, final int seconds,
                       final T value) {
        return this.set(RedisCacheIndex.DEFAULT_DB, key, seconds, value);
    }

    public <T> boolean set(final int dbIndex, final String key,
                           final int seconds, final T value) {
        if (key == null || value == null) {
            return false;
        }
        Object succeed = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                String ret;

                if (jedisUtil.isSimpleObj(value.getClass())) {
                    if (seconds == 0) {//CACHE_EXP_FOREVER=0
                        ret = jedis.set(key, value.toString());
                    } else {
                        ret = jedis.setex(key, seconds, value.toString());
                    }
                } else {
                    RuntimeSchema<T> runtimeSchema = (RuntimeSchema<T>) RuntimeSchema.createFrom(value.getClass());
                    byte[] bKey = SafeEncoder.encode(key);
                    byte[] bValue = ProtostuffIOUtil.toByteArray(value, runtimeSchema,
                            LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                    if (seconds == 0) {//CACHE_EXP_FOREVER=0
                        ret = jedis.set(bKey, bValue);
                    } else {
                        ret = jedis.setex(bKey, seconds, bValue);
                    }
                }

                return "OK".equals(ret);
            }
        });
        return succeed != null && (boolean) succeed;
    }

    /**
     * 获取list所有元素
     * 注意：比如存进去的时候是“1”,"2"，但是取出来的时候会变成"2","1"
     */
    public <T> List<T> getListAllRange(final String key, final Class<T> cls) {
        return this.getListAllRange(RedisCacheIndex.DEFAULT_DB, key, cls);
    }

    /**
     * 获取list所有元素
     */
    public <T> List<T> getListAllRange(final int dbIndex, final String key,
                                    final Class<T> cls) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                List<T> retList = Lists.newArrayList();
                if (jedisUtil.isSimpleObj(cls)){
                    List<String> stringList = jedis.lrange(key,0,-1);
                    if (stringList.size() > 0 ){
                        for (String str : stringList) {
                            T simpleObj = jedisUtil.createSimpleObj(str, cls);
                            retList.add(simpleObj);
                        }
                    }
                }else{
                    RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(cls);
                    byte[] bkey = SafeEncoder.encode(key);
                    List<byte[]> byteArrayList = jedis.lrange(bkey, 0, -1);
                    if (byteArrayList.size() > 0){
                        for (byte[] byteArray : byteArrayList) {
                            T obj = runtimeSchema.newMessage();
                            ProtostuffIOUtil.mergeFrom(byteArray,obj,runtimeSchema);
                            retList.add(obj);
                        }
                    }
                }
                return retList;
            }
        });
        return ret == null ? Collections.<T>emptyList() : (List<T>) ret;
    }

    /**
     * 将List保存到redis中
     */
    public <T> boolean pushList(final String key, final int seconds, final List<T> values) {
        return pushList(RedisCacheIndex.DEFAULT_DB, key, seconds, values);
    }

    public <T> boolean pushList(final int dbIndex, final String key,
                                final int seconds, final List<T> values) {
        if (key == null || values == null || values.size() == 0) {
            return false;
        }
        Object succeed = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                if (jedisUtil.isSimpleObj(values.get(0).getClass())) {
                    String[] array = new String[values.size()];
                    for (int i = 0; i < values.size(); i++) {
                        array[i] = values.get(i).toString();
                    }
                    ret = jedis.lpush(key, array);
                } else {
                    byte[] bKey = SafeEncoder.encode(key);
                    byte[][] array = new byte[values.size()][];
                    RuntimeSchema<T> runtimeSchema = (RuntimeSchema<T>) RuntimeSchema.createFrom(values.get(0).getClass());
                    for (int i = 0; i < values.size(); i++) {
                        array[i] = ProtostuffIOUtil.toByteArray(values.get(i), runtimeSchema,
                                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                    }
                    ret = jedis.lpush(bKey, array);
                }
                if (seconds != 0) {//CACHE_EXP_FOREVER=0
                    jedis.expire(key, seconds);
                }
                return ret != null && ret != 0;
            }
        });
        return succeed != null && (boolean) succeed;
    }


    // TODO: 2017/05/03 写到这里 
    /**
     * 设定一个hash对象
     *
     * @param key   哈希表中的key
     * @param field 域
     * @param value 值
     * @return 如果是第一次创建，则返回true，否则为false
     */
    public boolean setHash(final String key, final int seconds,
                           final String field, final Object value) {
        return setHash(RedisCacheIndex.DEFAULT_DB, key, seconds, field, value);
    }

    public boolean setHash(final int dbIndex, final String key,
                           final int seconds, final String field, final Object
                                   value) {
        if (key == null || field == null || value == null) {
            return false;
        }
        Object succeed = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                if (jedisUtil.isSimpleObj(value.getClass())) {
                    ret = jedis.hset(key, field, value.toString());
                } else {
                    byte[] bKey = SafeEncoder.encode(key);
                    byte[] bField = SafeEncoder.encode(field);
                    byte[] bValue = jedisUtil.serialize(value);
                    ret = jedis.hset(bKey, bField, bValue);
                }
                if (seconds != 0) {//CACHE_EXP_FOREVER=0
                    jedis.expire(key, seconds);
                }
                return ret != null && ret == 1;
            }
        });
        return succeed != null && (boolean) succeed;
    }

    public <T> T getHash(final String key, final String field,
                         final Class<T> cls) {
        return getHash(RedisCacheIndex.DEFAULT_DB, key, field, cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T getHash(final int dbIndex, final String key,
                         final String field, final Class<T> cls) {
        if (field == null)
            throw new IllegalArgumentException("field can not null");
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Object obj = null;
                if (jedisUtil.isSimpleObj(cls)) {
                    String str = jedis.hget(key, field);
                    if (str != null)
                        obj = jedisUtil.createSimpleObj(str, cls);
                } else {
                    byte[] bs = jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
                    if (bs != null) {
                        obj = jedisUtil.deserialize(bs);
                    }
                }
                return obj;
            }
        });
        return ret == null ? null : (T) ret;
    }

    public <T> List<T> getHash(final String key, final Class<T> cls,
                               final String... fields) {
        return getHash(RedisCacheIndex.DEFAULT_DB, key, cls, fields);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getHash(final int dbIndex, final String key,
                               final Class<T> cls, final String... fields) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                final byte[][] bfields = new byte[fields.length][];
                for (int i = 0; i < bfields.length; i++) {
                    bfields[i] = SafeEncoder.encode(fields[i]);
                }
                List<byte[]> bytes = jedis.hmget(SafeEncoder.encode(key), bfields);
                if (bytes == null) return null;
                List<T> retList = Lists.newArrayList();
                boolean isSimple = jedisUtil.isSimpleObj(cls);
                for (byte[] e : bytes) {
                    if (e == null) {
                        retList.add(null);
                        continue;
                    }
                    retList.add(isSimple ? jedisUtil.createSimpleObj(SafeEncoder.encode(e), cls) : (T) jedisUtil.deserialize(e));
                }
                return retList;
            }
        });
        return ret == null ? null : (List<T>) ret;
    }

    /**
     * 获取list的size
     */
    public long getListSize(final String key) {
        return getListSize(RedisCacheIndex.DEFAULT_DB, key);
    }

    /**
     * 获取list的size
     */
    public long getListSize(final int dbIndex, final String key) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.llen(key);
            }
        });
        return ret == null ? 0 : (long) ret;
    }


    /**
     * 获取key下面所有的值，string的
     */
    public <K, V> Map<K, V> getKeyAll(final String key) {
        return getKeyAll(RedisCacheIndex.DEFAULT_DB, key);
    }

    /**
     * 获取key下面所有的值，string的
     */
    public <K, V> Map<K, V> getKeyAll(final int dbIndex, final String key) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.hgetAll(key);
            }
        });
        return ret == null ? Collections.<K, V>emptyMap() : (Map<K, V>) ret;
    }

    /**
     * 获取指定指定index的list存储对象
     */
    public <T> T getList(final String key, final int index, final Class<T> cls) {
        return getList(RedisCacheIndex.DEFAULT_DB, key, index, cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T getList(final int dbIndex, final String key, final int index, final Class<T> cls) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Object obj = null;
                if (jedisUtil.isSimpleObj(cls)) {
                    String str = jedis.lindex(key, index);
                    if (str != null)
                        obj = jedisUtil.createSimpleObj(str, cls);
                } else {
                    byte[] bs = jedis.lindex(SafeEncoder.encode(key), index);
                    if (bs != null) {
                        obj = jedisUtil.deserialize(bs);
                    }
                }
                return obj;
            }
        });
        return ret == null ? null : (T) ret;
    }

    public <T> boolean lpushList(final String key, final T value) {
        return lpushList(RedisCacheIndex.DEFAULT_DB, key, 0, value);//CACHE_EXP_FOREVER=0

    }

    public <T> boolean lpushList(final int dbIndex, final String key, final T value) {
        return lpushList(dbIndex, key, 0, value);//CACHE_EXP_FOREVER=0

    }

    public <T> boolean lpushList(final String key, final Integer seconds, final T... values) {
        return lpushList(RedisCacheIndex.DEFAULT_DB, key, seconds, values);
    }

    public <T> boolean lpushList(final int dbIndex, final String key, final Integer seconds, final T... values) {
        if (key == null || values == null || values.length == 0) {
            return false;
        }
        Object succeed = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                byte[] bKey = SafeEncoder.encode(key);
                byte[][] array = new byte[values.length][];
                for (int i = 0; i < values.length; i++) {
                    array[i] = jedisUtil.serialize(values[i]);
                }
                ret = jedis.lpush(bKey, array);
                if (seconds != 0) {//CACHE_EXP_FOREVER=0
                    jedis.expire(key, seconds);
                }
                return ret != null && ret > 0;
            }
        });
        return succeed != null && (boolean) succeed;
    }

    public <T> List<T> rangeList(final String key, final Long start, final Long end) {
        return rangeList(RedisCacheIndex.DEFAULT_DB, key, start, end);
    }

    public <T> List<T> rangeList(final int dbIndex, final String key, final Long start, final Long end) {
        if (key == null || start == null || end == null) {
            return Lists.newArrayList();
        }
        Object obj = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                List<byte[]> rbyteList;
                byte[] bKey = SafeEncoder.encode(key);
                rbyteList = jedis.lrange(bKey, start, end);
                List<T> retList = Lists.newArrayList();
                for (byte[] rbyte : rbyteList) {
                    retList.add((T) jedisUtil.deserialize(rbyte));
                }
                return retList;
            }
        });
        return (List<T>) obj;
    }


    public <T> T rpopList(final String key) {
        return rpopList(RedisCacheIndex.DEFAULT_DB, key);
    }

    public <T> T rpopList(final int dbIndex, final String key) {
        if (key == null) {
            return null;
        }
        Object obj = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                byte[] bKey = SafeEncoder.encode(key);
                byte[] ret = jedis.rpop(bKey);
                return jedisUtil.deserialize(ret);
            }
        });
        return (T) obj;
    }

    public Long lenList(final String key) {
        return lenList(RedisCacheIndex.DEFAULT_DB, key);
    }

    public Long lenList(final int dbIndex, final String key) {
        if (key == null) {
            return 0l;
        }
        Object obj = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                byte[] bKey = SafeEncoder.encode(key);
                Long ret = jedis.llen(bKey);
                return ret;
            }
        });
        return (Long) obj;
    }

    /**
     * 对hash表中的某个元素执行增加操作，如果操作的field非数字，则结果返回null
     *
     * @param value 需要增加的值
     * @return 增加之后的值，如果操作的field非数字，则结果返回null
     */
    public Long incrHash(final String key, final String field, final long value) {
        return incrHash(RedisCacheIndex.DEFAULT_DB, key, field, value);
    }

    public Long incrHash(final int dbIndex, final String key, final String field, final long value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.hincrBy(key, field, value);
            }
        });
        return ret == null ? null : (Long) ret;
    }

    public Double incrHash(final String key, final String field, final double value) {
        return incrHash(RedisCacheIndex.DEFAULT_DB, key, field, value);
    }

    public Double incrHash(final int dbIndex, final String key, final String field, final double value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.hincrByFloat(key, field, value);
            }
        });
        return ret == null ? null : (Double) ret;
    }


    public <T> Long sadd(String key, T value) {
        return sadd(RedisCacheIndex.DEFAULT_DB, key, 0, value);//CACHE_EXP_FOREVER = 0
    }

    public <T> Long sadd(final int dbIndex, String key, T value) {
        return sadd(dbIndex, key, 0, value);//CACHE_EXP_FOREVER = 0
    }

    //这里不判断简单类型和对象,和其他的有所不同,请注意
    public <T> Long sadd(final int dbIndex, final String key, final int seconds, final T value) {
        if (key == null || value == null) {
            return 0L;
        }
        Object rs = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                byte[] bKey = SafeEncoder.encode(key);
                byte[] bValue = jedisUtil.serialize(value);
                ret = jedis.sadd(bKey, bValue);
                if (seconds != 0) {//CACHE_EXP_FOREVER = 0
                    jedis.expire(key, seconds);
                }
                return ret == null ? 0 : ret;
            }
        });
        return (Long) rs;
    }

    public <T> Set<T> smembers(final String key) {
        return smembers(RedisCacheIndex.DEFAULT_DB, key);
    }

    public <T> Set<T> smembers(final int dbIndex, final String key) {
        if (key == null) {
            return null;
        }
        Object rs = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Set<byte[]> res;
                Set<T> ret = Sets.newHashSet();
                byte[] bkey = SafeEncoder.encode(key);
                res = jedis.smembers(bkey);
                for (byte[] elem : res) {
                    ret.add((T) jedisUtil.deserialize(elem));
                }
                return ret;
            }
        });
        return (Set<T>) rs;

    }

    public Long slen(final String key) {
        return slen(RedisCacheIndex.DEFAULT_DB, key);
    }

    public Long slen(final int dbIndex, final String key) {
        if (key == null) {
            return 0l;
        }
        Object length = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                byte[] bKey = SafeEncoder.encode(key);
                ret = jedis.scard(bKey);
                return ret == null ? 0 : ret;
            }
        });
        return (Long) length;
    }


    public Long hlen(final String key) {
        return hlen(RedisCacheIndex.DEFAULT_DB, key);
    }

    public Long hlen(final int dbIndex, final String key) {
        if (key == null) {
            return 0l;
        }
        Object length = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                byte[] bKey = SafeEncoder.encode(key);
                ret = jedis.hlen(bKey);
                return ret == null ? 0 : ret;
            }
        });
        return (Long) length;
    }

    public <T> Boolean sisMember(final String key, final T val) {
        return sisMember(RedisCacheIndex.DEFAULT_DB, key, val);
    }

    public <T> Boolean sisMember(final int dbIndex, final String key, final T val) {
        if (key == null || val == null) {
            return false;
        }
        Object isMember = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                Boolean ret;
                byte[] bKey = SafeEncoder.encode(key);
                byte[] bVal = jedisUtil.serialize(val);
                ret = jedis.sismember(bKey, bVal);
                return ret == null ? false : ret;
            }
        });
        return (Boolean) isMember;
    }

    public <T> Long srem(final String key, final T val) {
        return srem(RedisCacheIndex.DEFAULT_DB, key, val);
    }

    public <T> Long srem(final int dbIndex, final String key, final T val) {
        if (key == null || val == null) {
            return 0l;
        }
        Object rs = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                Long ret;
                byte[] bKey = SafeEncoder.encode(key);
                byte[] bVal = jedisUtil.serialize(val);
                ret = jedis.srem(bKey, bVal);
                return ret == null ? 0 : ret;
            }
        });
        return (Long) rs;
    }

    public Long delete(final String key) {
        return delete(RedisCacheIndex.DEFAULT_DB, key);
    }

    public Long delete(final int dbIndex, final String key) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.del(key);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public Long delHashField(final String key, final String field) {
        return delHashField(RedisCacheIndex.DEFAULT_DB, key, field);
    }

    public Long delHashField(final int dbIndex, final String key, final String field) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.hdel(key, field);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public double incrBy(final String key, final double value) {
        return incrBy(RedisCacheIndex.DEFAULT_DB, key, value);
    }

    public double incrBy(final int dbIndex, final String key, final double value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.incrByFloat(key, value);
            }
        });
        return ret == null ? 0 : (double) ret;
    }

    public double decrBy(final String key, final double value) {
        return decrBy(RedisCacheIndex.DEFAULT_DB, key, value);
    }

    public double decrBy(final int dbIndex, final String key, final double value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.incrByFloat(key, -value);
            }
        });
        return ret == null ? 0 : (double) ret;
    }

    //+=values
    public long incrBy(final String key, final long value) {
        return incrBy(RedisCacheIndex.DEFAULT_DB, key, value);
    }

    public long incrBy(final int dbIndex, final String key, final long value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.incrBy(key, value);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public long decrBy(final String key, final long value) {
        return decrBy(RedisCacheIndex.DEFAULT_DB, key, value);
    }

    public long decrBy(final int dbIndex, final String key, final long value) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.incrBy(key, -value);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    //i++
    public long incr(final String key) {
        return incr(RedisCacheIndex.DEFAULT_DB, key);
    }

    public long incr(final int dbIndex, final String key) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.incr(key);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    /**
     * 获取资源key的锁，超过指定的时间无效，0表示无时间限制
     * 获取lock失败返回null
     *
     * @param key     资源name
     * @param timeout 超时时间, 单位ms
     * @return 返回与该锁配对的值，unlock时必须在key和该返回的value匹配上之后才可以unlock
     * 返回null表示lock已经被占用
     */
    public String lock(final String key, final long timeout) {
        return lock(RedisCacheIndex.DEFAULT_DB, key, timeout);
    }

    public String lock(final int dbIndex, final String key, final long timeout) {
        Object value = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                String retValue = String.valueOf((long) (Math.random() * Long.MAX_VALUE));
                long lastTimeout = MUTEX_EXP;
                if (timeout > 0) {
                    lastTimeout = timeout;
                }
                String ret = jedis.set(key, retValue, "NX", "PX", lastTimeout);
                return "OK".equals(ret) ? retValue : null;
            }
        });
        return value == null ? null : (String) value;
    }

    public void unlock(final String key, final String lockVal) {
        unlock(RedisCacheIndex.DEFAULT_DB, key, lockVal);
    }

    public void unlock(final int dbIndex, final String key, final String lockVal) {
        jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                if (lockVal.equals(jedis.get(key))) {
                    jedis.del(key);
                }
                return null;
            }
        });
    }





    /**
     * The TTL command returns the remaining time to live in seconds of a key
     * that has an {expire(String, int) EXPIRE} set. This introspection
     * capability allows a Redis client to check how many seconds a given key
     * will continue to be part of the dataset.
     *
     * @param key key
     * @return Integer reply, returns the remaining time to live in seconds of a
     * key that has an EXPIRE. In Redis 2.6 or older, if the Key does
     * not exists or does not have an associated expire, -1 is returned.
     * In Redis 2.8 or newer, if the Key does not have an associated
     * expire, -1 is returned or if the Key does not exists, -2 is
     * returned.
     */
    public Long ttl(final String key) {
        return ttl(RedisCacheIndex.DEFAULT_DB, key);
    }

    public Long ttl(final int dbIndex, final String key) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.ttl(key);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public Long expire(final String key, final int seconds) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                return jedis.expire(key, seconds);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public Long expire(final int dbIndex, final String key, final int seconds) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.expire(key, seconds);
            }
        });
        return ret == null ? 0l : (long) ret;
    }

    public String flushDB() {
        return flushDB(RedisCacheIndex.DEFAULT_DB);
    }

    public String flushDB(final int dbIndex) {
        Object ret = jedisUtil.runTask(jedisPool,new Callback() {
            @Override
            public Object onTask(Jedis jedis) {
                jedis.select(dbIndex);
                return jedis.flushDB();
            }
        });
        return ret == null ? "0" : (String) ret;
    }

}
