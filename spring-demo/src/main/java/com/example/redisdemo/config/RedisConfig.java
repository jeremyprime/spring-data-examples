package com.example.redisdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.valkey.springframework.data.valkey.connection.ValkeyConnectionFactory;
import io.valkey.springframework.data.valkey.connection.ValkeyStandaloneConfiguration;
// import io.valkey.springframework.data.valkey.connection.jedis.JedisConnectionFactory;
// import io.valkey.springframework.data.valkey.connection.lettuce.LettuceConnectionFactory;
import io.valkey.springframework.data.valkey.connection.valkeyglide.ValkeyGlideConnectionFactory;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public ValkeyConnectionFactory redisConnectionFactory() {
        ValkeyStandaloneConfiguration config = new ValkeyStandaloneConfiguration("localhost", 6379);
        // return new JedisConnectionFactory(config);
        // return new LettuceConnectionFactory(config);
        return new ValkeyGlideConnectionFactory(config);
    }

    @Bean
    public StringValkeyTemplate stringRedisTemplate() {
        return new StringValkeyTemplate(redisConnectionFactory());
    }

}
