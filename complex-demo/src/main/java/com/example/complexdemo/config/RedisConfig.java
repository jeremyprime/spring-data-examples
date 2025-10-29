package com.example.complexdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.valkey.springframework.data.valkey.cache.ValkeyCacheManager;
import io.valkey.springframework.data.valkey.connection.ValkeyConnectionFactory;
import io.valkey.springframework.data.valkey.connection.ValkeyStandaloneConfiguration;
import io.valkey.springframework.data.valkey.connection.valkeyglide.ValkeyGlideConnectionFactory;
import io.valkey.springframework.data.valkey.core.ValkeyTemplate;
import io.valkey.springframework.data.valkey.core.StringValkeyTemplate;
import io.valkey.springframework.data.valkey.repository.configuration.EnableValkeyRepositories;
import io.valkey.springframework.data.valkey.serializer.GenericJackson2JsonValkeySerializer;
import io.valkey.springframework.data.valkey.serializer.Jackson2JsonValkeySerializer;
import io.valkey.springframework.data.valkey.serializer.StringValkeySerializer;

@Configuration
@EnableCaching
@EnableValkeyRepositories(basePackages = "com.example.complexdemo.repository")
@ComponentScan(basePackages = "com.example.complexdemo")
public class RedisConfig {

    @Bean
    public ValkeyConnectionFactory redisConnectionFactory() {
        ValkeyStandaloneConfiguration config = new ValkeyStandaloneConfiguration("localhost", 6379);
        return ValkeyGlideConnectionFactory.createValkeyGlideConnectionFactory(config);
    }

    @Bean
    public StringValkeyTemplate stringValkeyTemplate() {
        return new StringValkeyTemplate(redisConnectionFactory());
    }

    @Bean
    public ValkeyTemplate<String, Object> valkeyTemplate() {
        ValkeyTemplate<String, Object> template = new ValkeyTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringValkeySerializer());
        template.setValueSerializer(new GenericJackson2JsonValkeySerializer());
        template.setHashKeySerializer(new StringValkeySerializer());
        template.setHashValueSerializer(new GenericJackson2JsonValkeySerializer());
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CacheManager cacheManager() {
        return ValkeyCacheManager.builder(redisConnectionFactory()).build();
    }

}
