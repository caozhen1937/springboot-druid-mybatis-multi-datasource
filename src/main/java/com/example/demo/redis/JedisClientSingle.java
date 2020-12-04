package com.example.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;

/**
 * Created by caozhen on 2018/11/23
 */
// 调用需要@Autowired
@Service
public class JedisClientSingle implements JedisClient {

    static Logger logger = LoggerFactory.getLogger(JedisClientSingle.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    public JedisPool jedisPool;

    public Jedis getResource() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

        } catch (JedisException e) {
            logger.error(e.getMessage());
        }
        return jedis;
    }

    /**
     * 归还资源
     *
     * @param jedis
     */
    public void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     *
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }


    @Override
    public String get(String key) {

        Jedis resource = getResource();
        String string = resource.get(key);
        resource.close();

        return string;
    }

    @Override
    public String set(String key, String value) {
        Jedis resource = getResource();
        String string = resource.set(key, value);
        resource.close();

        return string;
    }

    @Override
    public String hget(String hkey, String value) {
        Jedis resource = getResource();
        String string = resource.hget(hkey, value);
        resource.close();
        return string;
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Jedis resource = getResource();
        Long hset = resource.hset(hkey, key, value);
        resource.close();

        return hset;
    }

    @Override
    public Long incr(String key) {
        Jedis resource = getResource();
        Long incr = resource.incr(key);
        resource.close();
        return incr;
    }

    @Override
    public Long expire(String key, Integer second) {
        Jedis resource = getResource();
        Long expire = resource.expire(key, second);

        resource.close();
        return expire;
    }

    @Override
    public Long ttl(String key) {
        Jedis resource = getResource();
        Long ttl = resource.ttl(key);
        resource.close();
        return ttl;
    }

    @Override
    public Long del(String key) {
        Jedis resource = getResource();
        Long del = resource.del(key);
        resource.close();
        return del;
    }

    @Override
    public Long hdel(String hkey, String key) {
        Jedis resource = getResource();
        Long hdel = resource.hdel(hkey, key);
        resource.close();
        return hdel;
    }


    /**
     * 分布式锁 加锁
     *
     * @param jedisPool
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return 是否获取成功
     */
    public Boolean tryGetDistributedLock(JedisPool jedisPool, String lockKey, String requestId, int expireTime) {
        // 保证 设置锁操作 和 对锁加过期时间操作 的原子性，避免前者执行成功，后者执行失败，从而避免了死锁
        String res = jedisPool.getResource().set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(res)) {
            return true;
        }

        return false;
    }

    /**
     * 分布式锁 解锁
     *
     * @param jedisPool
     * @param lockKey
     * @param requestId
     * @return 是否解锁成功
     */
    public static boolean releaseDistributedLock(JedisPool jedisPool, String lockKey, String requestId) {

        // lua 代码，保证 验证锁是否存在操作 和删除锁操作 的原子性，保证谁加锁谁来解锁，避免任一线程解锁
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisPool.getResource().eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }


}
