package com.education.hjj.bz.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Copyright: Copyright (c) 2018
 *
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: ruyi
 * @date: 2018/11/11 15:15
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2018/11/11     ruyi           v1.0.0               修改原因
 */
@Component
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 认证 授权 密码check时使用
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplateCache;

    /**
     * 其他使用
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 前缀
     */
    private static final String KEY_PREFIX_VALUE = "network:value:";
    private static final String KEY_PREFIX_SET   = "network:set:";
    private static final String KEY_PREFIX_LIST  = "network:list:";
    private static final String KEY_PREFIX_HASH  = "network:hash:";

    /**
     * 过期设定
     *
     * @param key
     * @param time
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 检查可以是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 删除指定key
     *
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplateCache.delete(key[0]);
            } else {
                redisTemplateCache.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除key
     *
     * @param keys
     */
    public void del(Collection keys) {
        redisTemplateCache.delete(keys);
    }

    /**
     * 获取指定key的数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplateCache.opsForValue().get(key);
    }

    /**
     * 设定指定key的值
     *
     * @param key
     * @param object
     */
    public void set(String key, Object object) {
        redisTemplateCache.opsForValue().set(key, object);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplateCache.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 使用scan命令 查询某些前缀的key
     *
     * @param key
     * @return
     */
    public Set<String> scan(String key) {
        // 类型见证者
        Set<String> execute = this.redisTemplateCache.execute((RedisCallback<Set<String>>) (connection) -> {
            Set<String> binaryKeys = new HashSet<>();

            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
            while (cursor.hasNext()) {
                binaryKeys.add(new String(cursor.next()));
            }
            return binaryKeys;
        });
        return execute;
    }

    /**
     * 使用scan命令 查询某些前缀的key 有多少个
     * 用来获取当前session数量,也就是在线用户
     *
     * @param key
     * @return
     */
    public Long scanSize(String key) {
        long dbSize = this.redisTemplateCache.execute((RedisCallback<Long>) (connection) -> {
            long count = 0L;
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(key).count(1000).build());
            while (cursor.hasNext()) {
                cursor.next();
                count++;
            }
            return count;
        });
        return dbSize;
    }

//---------------------------------------------------------新增缓存 start ------------------------------------------
    /**
     * 缓存value操作
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheValue(String k, String v, long time) {
        String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            valueOps.set(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to Redis [" + key + "] Fail, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存value操作
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheValue(String k, String v) {
        return cacheValue(k, v, -1);
    }

    /**
     * 缓存 object value
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheObject(String k, Object v) {
        String json = JSON.toJSONString(v);
        return cacheValue(k, json);
    }

    /**
     * 缓存 object value
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheObject(String k, Object v, long time) {
        String json = JSON.toJSONString(v);
        return cacheValue(k, json, time);
    }

    /**
     * 缓存set value操作
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSetValue(String k, String v, long time) {
        String key = KEY_PREFIX_SET + k;
        try {
            SetOperations<String, String> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to Redis [" + key + "] Fail ..., value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存set  value
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSetValue(String k, String v) {
        return cacheSetValue(k, v, -1);
    }

    /**
     * 缓存set
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSet(String k, Set<String> v, long time) {
        String key = KEY_PREFIX_SET + k;
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(key, v.toArray(new String[v.size()]));
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to Redis [" + key + "]Fail set, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存set
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String k, Set<String> v) {
        return cacheSet(k, v, -1);
    }

    /**
     * list缓存 value
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheListValue(String k, String v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPush(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to CacheListValue [" + key + "] Fail, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存list value
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheListValue(String k, String v) {
        return cacheListValue(k, v, -1);
    }

    /**
     * 缓存list
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheList(String k, List<String> v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            long l = listOps.rightPushAll(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to Cache List [" + key + "] Fail, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存list
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheList(String k, List<String> v) {
        return cacheList(k, v, -1);
    }

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
        String key = KEY_PREFIX_HASH + k;
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            hashOps.put(key, hashKey, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put TO Cache [" + key + "] Fail , value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存hash key-value
     *
     * @param k
     * @param hashKey
     * @param v
     * @return
     */
    public boolean cacheHashValue(String k, String hashKey, String v) {
        return cacheHashValue(k, hashKey, v, -1);
    }

    /**
     * 缓存 hash
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheHash(String k, Map<String, String> v, long time) {
        String key = KEY_PREFIX_HASH + k;
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            hashOps.putAll(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("Put to Cache [" + key + "] Fail, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存 hash
     *
     * @param k
     * @param v
     * @return
     */
    public boolean cacheHash(String k, Map<String, String> v) {
        return cacheHash(k, v, -1);
    }
//---------------------------------------------------------新增缓存 end ------------------------------------------


//---------------------------------------------------------读取缓存 start ------------------------------------------
    /**
     * 获取缓存
     *
     * @param k
     * @return
     */
    public String getValue(String k) {
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(KEY_PREFIX_VALUE + k);
        } catch (Throwable t) {
            logger.error("Get Value Fail : key[" + KEY_PREFIX_VALUE + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取缓存set数据
     *
     * @param k
     * @return
     */
    public Set<String> getSet(String k) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            return setOps.members(KEY_PREFIX_SET + k);
        } catch (Throwable t) {
            logger.error("Get Set Fail : key[" + KEY_PREFIX_SET + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取list缓存
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<String> getList(String k, long start, long end) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.range(KEY_PREFIX_LIST + k, start, end);
        } catch (Throwable t) {
            logger.error("Get List Fail : key[" + KEY_PREFIX_LIST + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取总条数, 可用于分页
     *
     * @param k
     * @return
     */
    public long getListSize(String k) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.size(KEY_PREFIX_LIST + k);
        } catch (Throwable t) {
            logger.error("Get List Size Fail : key[" + KEY_PREFIX_LIST + k + "], error[" + t + "]");
        }
        return 0;
    }

    /**
     * 获取总条数, 可用于分页
     *
     * @param listOps
     * @param k
     * @return
     */
    public long getListSize(ListOperations<String, String> listOps, String k) {
        try {
            return listOps.size(KEY_PREFIX_LIST + k);
        } catch (Throwable t) {
            logger.error("Get List Size Fail key[" + KEY_PREFIX_LIST + k + "], error[" + t + "]");
        }
        return 0;
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
            return hashOps.get(KEY_PREFIX_HASH + k, hashKey);
        } catch (Throwable t) {
            logger.error("Get Hash Value Fail key[" + KEY_PREFIX_HASH + k + "], hashKey[" + hashKey + "], error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取缓存
     *
     * @param k
     * @return
     */
    public Map<String, String> getHash(String k) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            return hashOps.entries(KEY_PREFIX_HASH + k);
        } catch (Throwable t) {
            logger.error("Get Hash Fail : key[" + KEY_PREFIX_HASH + k + "], error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取hash缓存 所有key
     *
     * @param k
     * @return
     */
    public Set<String> getHashKeys(String k) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            return hashOps.keys(KEY_PREFIX_HASH + k);
        } catch (Throwable t) {
            logger.error("Get Key Fail : key[" + KEY_PREFIX_HASH + k + "], error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取hash缓存 所有values
     *
     * @param k
     * @return
     */
    public List<String> getHashValues(String k) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            return hashOps.values(KEY_PREFIX_HASH + k);
        } catch (Throwable t) {
            logger.error("Get KeyValue Fail : key[" + KEY_PREFIX_HASH + k + "], error[" + t + "]");
        }
        return null;
    }
//---------------------------------------------------------读取缓存 end ------------------------------------------

// --------------------------------------------------------- 移除 start ------------------------------------------
    /**
     * 移除缓存
     *
     * @param k
     * @return
     */
    public boolean removeValue(String k) {
        return remove(KEY_PREFIX_VALUE + k);
    }

    /**
     * 移除缓存
     *
     * @param k
     * @return
     */
    public boolean removeSet(String k) {
        return remove(KEY_PREFIX_SET + k);
    }

    /**
     * 移除缓存
     *
     * @param k
     * @return
     */
    public boolean removeList(String k) {
        return remove(KEY_PREFIX_LIST + k);
    }

    /**
     * 移除缓存
     *
     * @param k
     * @return
     */
    public boolean removeHash(String k) {
        return remove(KEY_PREFIX_HASH + k);
    }

    public boolean removeHash(String k, String... hashKeys) {
        return removeHashList(KEY_PREFIX_HASH + k, hashKeys);
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            logger.error("Delete Key Fail : key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    public boolean removeHashList(String key, String... hashKeys) {
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
            return true;
        } catch (Throwable t) {
            logger.error("Delete Key Fail : key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 移除缓存hash 的value
     *
     * @param k
     * @param hashKey
     * @return
     */
    public boolean removeHashValue(String k, String hashKey) {
        try {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            hashOps.delete(KEY_PREFIX_HASH + k, hashKey);
            return true;
        } catch (Throwable t) {
            logger.error("Remove Key Fail : key[" + KEY_PREFIX_HASH + k + "],hashKey[" + hashKey + "], error[" + t + "]");
        }
        return false;
    }
//--------------------------------------------------------- 移除 end ------------------------------------------
}
