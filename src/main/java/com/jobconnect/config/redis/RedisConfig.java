package com.jobconnect.config.redis;

import com.jobconnect.job.dto.JobResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration singleJobCacheConfig =  RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(15))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(JobResponseDto.class)));

        RedisCacheConfiguration jobsCacheConfig =  RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(JobResponseDto.class)));

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(singleJobCacheConfig)
                .withCacheConfiguration("JOBS_CACHE", jobsCacheConfig)
                .build();


    }
}
