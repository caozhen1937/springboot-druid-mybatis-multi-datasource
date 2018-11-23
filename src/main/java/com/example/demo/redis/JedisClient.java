package com.example.demo.redis;

/**
 * Created by caozhen on 2018/11/23
 */

public interface JedisClient {

  public String get(String key);
  public String set(String key, String value);
  public String hget(String hkey, String value);
  public Long hset(String hkey, String key, String value);
  public Long incr(String key);
  public Long expire(String key, Integer second);
  public Long ttl(String key);
  public Long del(String key);
  public Long hdel(String hkey, String key);

}
