package com.example.redis_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.valkey.springframework.data.valkey.connection.ValkeyConnectionFactory;
import io.valkey.springframework.data.valkey.connection.ValkeyStandaloneConfiguration;
import io.valkey.springframework.data.valkey.connection.valkeyglide.ValkeyGlideConnectionFactory;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;

@Configuration
public class RedisDemoConfiguration {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public ValkeyConnectionFactory redisConnectionFactory() {
        ValkeyStandaloneConfiguration config = new ValkeyStandaloneConfiguration(host, port);
        return ValkeyGlideConnectionFactory.createValkeyGlideConnectionFactory(config);
    }

    @Bean
    public StringValkeyTemplate stringValkeyTemplate() {
        return new StringValkeyTemplate(redisConnectionFactory());
    }

}
