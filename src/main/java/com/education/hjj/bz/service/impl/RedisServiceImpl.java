package com.education.hjj.bz.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.education.hjj.bz.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.service.IRedisService;

@Service
public class RedisServiceImpl implements IRedisService {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    /**
     * 前缀
     */
    private static final String KEY_PREFIX_VALUE = "laijiajiao:value:";

	@Resource
    private RedisTemplate redisTemplate;
 
    @Override
    public void setKey(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, 3600, TimeUnit.SECONDS);//60分钟过期
    }
 
    @Override
    public String getValue(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }
 
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 缓存value操作
     *
     * @param k
     * @param v
     * @param time
     * @return
     */

    /**
     * 缓存hash key-value
     *
     * @param k
     * @param hashKey
     * @param v
     * @param time
     * @return
     */
    public boolean cacheHashValue(String k, String hashKey, String v, long time) {
        String key = KEY_PREFIX_VALUE + k;
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            hashOps.put(key, hashKey, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 获取缓存 hash  value
     *
     * @param k
     * @return
     */
    public String getHashValue(String k, String hashKey) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            return hashOps.get(KEY_PREFIX_VALUE + k, hashKey);
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + KEY_PREFIX_VALUE + k + "], hashKey[" + hashKey + "], error[" + t + "]");
        }
        return null;
    }
}
