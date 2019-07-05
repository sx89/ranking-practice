package com.sxfdu.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 用来封装redistemplate
 *
 * @author sunxu93@163.com
 * @date 19/7/4/004 21:19
 */
@Service

public class RedisService {
    private static double size = Math.pow(2, 32);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param offset 位 8Bit=1Byte
     * @return
     */
    public boolean setBit(String key, long offset, boolean isShow) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.setBit(key, offset, isShow);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.getBit(key, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置写入缓存时间的set
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 查看key值是否存在
     *
     * @param key
     * @return
     */
    public boolean exist(final String key) {
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        if (exist(key)) {
            return redisTemplate.delete(key);
        } else {
            return false;
        }

    }

    /**
     * 批量删除key
     *
     * @param keys
     * @return
     */
    public boolean remove(final String... keys) {
        try {
            for (String temp : keys) {
                if (exist(temp)) {
                    remove(keys);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object get(final String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }


    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }


}
