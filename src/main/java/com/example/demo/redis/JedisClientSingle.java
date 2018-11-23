package com.example.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by caozhen on 2018/11/23
 */
// 调用需要@Autowired
@Service
public class JedisClientSingle implements JedisClient {

    static Logger logger = LoggerFactory.getLogger(JedisClientSingle.class);

    @Autowired
    public JedisPool jedisPool;

    public Jedis getResource(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

        } catch (JedisException e){
            logger.error(e.getMessage());
        }
        return jedis ;
    }

    /**
     * 归还资源
     *
     * @param jedis
     */
    public void returnBrokenResource(Jedis jedis){
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     *
     * @param jedis
     */
    public void returnResource(Jedis jedis){
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
}
