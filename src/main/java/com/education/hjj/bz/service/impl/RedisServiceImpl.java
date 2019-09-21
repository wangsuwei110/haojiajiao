package com.education.hjj.bz.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.service.IRedisService;

@Service
public class RedisServiceImpl implements IRedisService {

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
}
