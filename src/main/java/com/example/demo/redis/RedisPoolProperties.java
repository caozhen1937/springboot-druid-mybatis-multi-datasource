package com.example.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by caozhen on 2018/11/23
 */

@ConfigurationProperties(prefix = "spring.redis")
//@PropertySource(value = "classpath:application.properties")
public class RedisPoolProperties {
    private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @Value("${spring.redis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private Integer database;


    protected JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    protected JedisPool jedisPool;

    public RedisPoolProperties() {
    }

    public JedisPoolConfig getJedisPoolConfig() {
        this.jedisPoolConfig.setMaxIdle(this.maxIdle);
        return this.jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public Integer getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public JedisPool getJedisPool() {
        if (this.password != null && this.password.trim().length() > 0) {
            this.jedisPool = new JedisPool(this.jedisPoolConfig, this.host, this.port, 6000, this.password, this.database,(String)null);
        } else {
            this.jedisPool = new JedisPool(this.jedisPoolConfig, this.host, this.port, 6000,(String)null, this.database,(String)null);
        }

        return this.jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
