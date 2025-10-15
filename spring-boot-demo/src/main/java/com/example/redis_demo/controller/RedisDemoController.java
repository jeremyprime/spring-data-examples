package com.example.redis_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class RedisDemoController {

    @Autowired
    private StringValkeyTemplate redisTemplate;

    @PostMapping("/set")
    public ResponseEntity<String> set(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return ResponseEntity.ok("OK");
    }

    @SuppressWarnings("null")
    @GetMapping("/get")
    public ResponseEntity<String> get(@RequestParam String key) {
        System.out.println("Connection Factory: " + redisTemplate.getConnectionFactory().getClass().getName());
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisTemplate.delete(key);
        return ResponseEntity.ok("OK");
    }

}
