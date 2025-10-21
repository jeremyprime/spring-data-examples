package com.example.redisdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.valkey.springframework.data.valkey.connection.ValkeyConnectionFactory;
import io.valkey.springframework.data.valkey.connection.ValkeyStandaloneConfiguration;
import io.valkey.springframework.data.valkey.connection.valkeyglide.ValkeyGlideConnectionFactory;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;

@Configuration
public class RedisConfig {

    @Value("${spring.data.valkey.host}")
    private String host;

    @Value("${spring.data.valkey.port}")
    private int port;

    @Bean
    public ValkeyConnectionFactory redisConnectionFactory() {
        ValkeyStandaloneConfiguration config = new ValkeyStandaloneConfiguration(host, port);
        return ValkeyGlideConnectionFactory.createValkeyGlideConnectionFactory(config);
    }

    @Bean
    public StringValkeyTemplate stringRedisTemplate() {
        return new StringValkeyTemplate(redisConnectionFactory());
    }

}
