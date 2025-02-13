package com.kantboot.util.cache;

import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Getter
@Component
public class CacheUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public CacheUtil setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        return this;
    }

    /**
     * 加锁
     * @param key 键
     * @param timeout 锁定时间
     * @param unit 时间单位
     * @return 成功锁定返回true，否则返回false
     */
    public Boolean lock(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", timeout, unit);
    }

    /**
     * 加锁，无超时时间
     * @param key 键
     * @return 成功锁定返回true，否则返回false
     */
    public Boolean lock(String key) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1");
    }

    /**
     * 解锁
     * @param key 键
     */
    public void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除key
     * @param key 键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除开头为key的所有key
     * @param key 键
     */
    public void deleteByPrefix(String key) {
        // 获取所有key开头redis的key
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        for (String k : keys) {
//            System.err.println("删除key：" + k);
            stringRedisTemplate.delete(k);
        }
    }

    /**
     * 批量删除key
     * @param keys 键集合
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 获取开头为key的所有key
     * @param key 键
     * @return key集合
     */
    public Set<String> keysByPrefix(String key) {
        return stringRedisTemplate.keys(key + "*");
    }

    /**
     * 是否存在key
     * @param key 键
     * @return 存在返回true，否则返回false
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 设置成功返回true，否则返回false
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param date 时间
     * @return 设置成功返回true，否则返回false
     */
    public Boolean expireAt(String key, Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    /**
     * 返回 key 所储存的值的类型
     * @param key 键
     * @return 数据类型
     */
    public DataType type(String key) {
        return stringRedisTemplate.type(key);
    }

    /**
     * 设置指定 key 的值
     * @param key 键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     */
    public void setEx(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取字符串的长度
     * @param key 键
     * @return 字符串的长度
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }
}
