package com.education.hjj.bz.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.education.hjj.bz.util.common.StringUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置，项目启动注入JedisPool
 * http://www.cnblogs.com/GodHeng/p/9301330.html
 * @author dolyw.com
 * @date 2018/9/5 10:35
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "redis")
public class JedisConfig {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisPool redisPoolFactory() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            // JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
            String pwd = StringUtil.isBlank(password) ? null : password;
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pwd);
            logger.info("初始化Redis连接池JedisPool成功!地址: " + host + ":" + port);
            return jedisPool;
        } catch (Exception e) {
            logger.error("初始化Redis连接池JedisPool异常:" + e.getMessage());
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
}
