package com.example.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by caozhen on 2018/11/23
 */
@Configuration
@EnableConfigurationProperties({RedisPoolProperties.class})
@ConditionalOnClass({JedisPoolConfig.class, JedisPool.class, Jedis.class})
public class RedisAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);
    @Autowired
    private RedisPoolProperties redisPoolProperties;

    public RedisAutoConfiguration() {
    }

    @Bean
    public JedisPool jedisPool() {
        logger.info("RedisAutoConfiguration.jedisPool ---> " + this.redisPoolProperties.getJedisPool());
        return this.redisPoolProperties.getJedisPool();
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        logger.info("RedisAutoConfiguration.jedisPoolConfig ---> " + this.redisPoolProperties.getJedisPoolConfig());
        return this.redisPoolProperties.getJedisPoolConfig();
    }

}

