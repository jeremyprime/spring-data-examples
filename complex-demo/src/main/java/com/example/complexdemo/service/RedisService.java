package com.example.complexdemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import io.valkey.springframework.data.valkey.core.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringValkeyTemplate stringRedisTemplate;

    @Autowired
    private ValkeyTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // String operations
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    // Hash operations
    public void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Object getHashField(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // List operations
    public void pushToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    // Set operations
    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // Sorted Set operations
    public void addToSortedSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> getSortedSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    // Expiration
    public void setExpiration(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    // Pipeline operations
    public void pipelineOperations() {
        redisTemplate.executePipelined((ValkeyCallback<Object>) connection -> {
            connection.set("pipe1".getBytes(), "value1".getBytes());
            connection.set("pipe2".getBytes(), "value2".getBytes());
            connection.set("pipe3".getBytes(), "value3".getBytes());
            return null;
        });
    }

    // Transaction operations
    public void transactionOperations() {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(ValkeyOperations operations) {
                operations.multi();
                operations.opsForValue().set("tx1", "value1");
                operations.opsForValue().set("tx2", "value2");
                return operations.exec();
            }
        });
    }

    // Atomic counter operations
    public Long incrementCounter(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public Long incrementCounterBy(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Long decrementCounter(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    // TTL operations
    public void setWithExpiration(String key, String value, long seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    public Long getTimeToLive(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // Bound operations
    public void boundListOperations(String key) {
        BoundListOperations<String, String> boundList = stringRedisTemplate.boundListOps(key);
        boundList.rightPush("bound1");
        boundList.rightPush("bound2");
        boundList.rightPush("bound3");
    }

    public void boundHashOperations(String key) {
        BoundHashOperations<String, String, String> boundHash = stringRedisTemplate.boundHashOps(key);
        boundHash.put("field1", "value1");
        boundHash.put("field2", "value2");
    }

    public List<String> getBoundList(String key) {
        return stringRedisTemplate.boundListOps(key).range(0, -1);
    }

    public Map<Object, Object> getBoundHash(String key) {
        return stringRedisTemplate.boundHashOps(key).entries();
    }

    // Custom serialization with XML
    public void setWithXmlSerialization(String key, Object value) {
        try {
            String xmlValue = "<object>" + objectMapper.writeValueAsString(value) + "</object>";
            stringRedisTemplate.opsForValue().set(key, xmlValue);
        } catch (Exception e) {
            throw new RuntimeException("XML serialization failed", e);
        }
    }

    public String getXmlSerialized(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
