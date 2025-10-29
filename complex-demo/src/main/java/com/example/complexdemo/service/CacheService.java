package com.example.complexdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private StringValkeyTemplate stringRedisTemplate;

    @Cacheable(value = "userCache", key = "#p0")
    public String getUserData(String userId) {
        // Simulate expensive operation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "User data for: " + userId;
    }

    @CacheEvict(value = "userCache", key = "#p0")
    public void evictUserCache(String userId) {
        System.out.println("Evicted cache for user: " + userId);
    }

    public void manualCacheOperations() {
        stringRedisTemplate.opsForValue().set("manual:cache:key", "cached value");
        String cached = stringRedisTemplate.opsForValue().get("manual:cache:key");
        System.out.println("Manual cache value: " + cached);
    }

}
