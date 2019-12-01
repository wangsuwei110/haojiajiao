package com.education.hjj.bz.service;

import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public interface IRedisService {



	/**
     * 设置key-value
     * @param key
     * @param value
     */
    void setKey(String key, String value);
 
    /**
     * 获取key
     * @param key
     * @return
     */
    String getValue(String key);
 
    /**
     * 删除key
     * @param key
     */
    void delete(String key);

    /**
     * 缓存value操作
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheHashValue(String k, String hashKey, String v, long time);

    /**
     * 获取value操作
     *
     * @param k
     * @param hashKey
     * @return
     */
     String getHashValue(String k, String hashKey);
}
