package com.geekhalo.like.infra;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Configuration
//@ConditionalOnBean(RedisConnectionFactory.class)
//@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CacheConfiguration {
    @Bean
//    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Long> targetCountRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return redisTemplate;
    }


    @Bean
//    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, DislikeTargetCount> dislikeTargetCountRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, DislikeTargetCount> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<DislikeTargetCount> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(DislikeTargetCount.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
//    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, LikeTargetCount> likeTargetCountRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, LikeTargetCount> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<LikeTargetCount> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(LikeTargetCount.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
