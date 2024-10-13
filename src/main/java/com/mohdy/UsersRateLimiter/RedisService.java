package com.mohdy.UsersRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void incKey(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveDataWithExpiration(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
//        redisTemplate.expire(key, expirationInSeconds, TimeUnit.SECONDS);
    }

    public boolean deleteData(String key) {
        return redisTemplate.delete(key);
    }

    // I added it to expire username_trials
    public void setExpiration(String key, long timeoutInSeconds) {
        redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
    }

    // I added it to get username_trials value
    public Object getKeyValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // I added it to get the time of abnormal behavior
    public void logAbnormalBehavior(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("There is abnormal behavior from user: " + username + " at " + currentTime);
    }
}
