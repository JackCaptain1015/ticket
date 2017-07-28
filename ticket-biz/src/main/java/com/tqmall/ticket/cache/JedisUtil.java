package com.tqmall.ticket.cache;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by wurenzhi on 2017/04/22.
 */
@Component
@Slf4j
public class JedisUtil {

    private static final List<Class<?>> SIMPLE_CLASS_OBJ = Lists.newArrayList();

    static {
        SIMPLE_CLASS_OBJ.add(Number.class);
        SIMPLE_CLASS_OBJ.add(String.class);
        SIMPLE_CLASS_OBJ.add(Boolean.class);
    }

    public boolean isSimpleObj(Class<?> classObj) {
        for (Class<?> c : SIMPLE_CLASS_OBJ) {
            if (c.isAssignableFrom(classObj))
                return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T createSimpleObj(String arg, Class<T> cls) {
        T ret = null;
        Constructor[] constructors = cls.getDeclaredConstructors();
        for (Constructor t : constructors) {
            Class[] parameterTypes = t.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(String.class)) {
                try {
                    ret = (T) t.newInstance(arg);
                } catch (Exception e) {
                    log.error("create simple obj error: " + e.getMessage(), e);
                }
                break;
            }
        }
        return ret;
    }

    /**主要供外部调用方法*/
    public  Object runTask(JedisPool jedisPool,Callback callback) {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            return callback.onTask(jedis);
        } catch (JedisException e) {
            broken = this.handleJedisException(e);
        } catch (Exception e) {
            log.error("Redis runTask error: ", e);
        } finally {
            this.closeResource(jedisPool,jedis, broken);
            jedis = null;
        }
        return null;
    }

    /**
     * 对象序列化成二进制
     * */
    public byte[] serialize(Object object) {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException("设定缓存的对象：" + object.getClass() + "无法序列化，确保 implements Serializable");
        }
        ObjectOutputStream objOS = null;
        ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
        try {
            objOS = new ObjectOutputStream(byteAOS);
            objOS.writeObject(object);
            return byteAOS.toByteArray();
        } catch (Exception e) {
            log.error("serialize error: " + e.getMessage());
        } finally {
            try {
                if (objOS != null) objOS.close();
                byteAOS.close();
            } catch (IOException e) {
                log.error("serialize close error : " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 二进制反序列化成对象
     * */
    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream byteAIS = new ByteArrayInputStream(bytes);
        ObjectInputStream objIS = null;
        try {
            objIS = new ObjectInputStream(byteAIS);
            return objIS.readObject();
        } catch (Exception e) {
            log.error("deserialize error: " + e.getMessage());
        } finally {
            try {
                byteAIS.close();
                if (objIS != null) objIS.close();
            } catch (IOException e) {
                log.error("deserialize close error: " + e.getMessage());
            }
        }
        return null;
    }



    /**供内部调用方法*/
    /**
     * Return jedis connection to the pool, call different return methods depends on the conectionBroken status.
     */
    private void closeResource(JedisPool jedisPool ,Jedis jedis, boolean conectionBroken) {
        try {
            if (conectionBroken) {
                jedisPool.returnBrokenResource(jedis);
            } else {
                jedisPool.returnResource(jedis);
            }
        } catch (Exception e) {
            log.error("return back jedis failed, will fore close the jedis.", e);
            jedis.close();
        }
    }

    /**
     * Handle jedisException, write log and return whether the connection is broken.
     */
    private boolean handleJedisException(JedisException jedisException) {
        if (jedisException instanceof JedisConnectionException) {
            log.error("Redis connection lost.", jedisException);
        } else if (jedisException instanceof JedisDataException) {
            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
                log.error("Redis connection are read-only slave.", jedisException);
            } else {
                // dataException, isBroken=false
                return false;
            }
        } else {
            log.error("Jedis exception happen.", jedisException);
        }
        return true;
    }

}
