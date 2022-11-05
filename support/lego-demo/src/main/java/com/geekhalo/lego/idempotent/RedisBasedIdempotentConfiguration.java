package com.geekhalo.lego.idempotent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekhalo.lego.annotation.idempotent.Idempotent;
import com.geekhalo.lego.core.idempotent.IdempotentExecutorFactory;
import com.geekhalo.lego.core.idempotent.IdempotentMetaParser;
import com.geekhalo.lego.core.idempotent.support.*;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class RedisBasedIdempotentConfiguration {

    @Bean
    public PointcutAdvisor idempotentPointcutAdvisor(IdempotentInterceptor idempotentInterceptor){
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, Idempotent.class),
                idempotentInterceptor);
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor(IdempotentMetaParser parser,
                                                       IdempotentExecutorFactories factories){
        return new IdempotentInterceptor(parser, factories);
    }

    @Bean
    public IdempotentMetaParser idempotentMetaParser(){
        return new IdempotentAnnParser();
    }

    @Bean
    public IdempotentExecutorFactories idempotentExecutorFactories(
            Map<String, IdempotentExecutorFactory> factoryMap){
        return new IdempotentExecutorFactories(factoryMap);
    }

    @Bean("redisExecutorFactory")
    public IdempotentExecutorFactory idempotentExecutorFactory(IdempotentKeyParser idempotentKeyParser,
                                                               ExecutionResultSerializer serializer,
                                                               ExecutionRecordRepository executionRecordRepository){
        SimpleIdempotentExecutorFactory simpleIdempotentExecutorFactory
                = new SimpleIdempotentExecutorFactory();
        simpleIdempotentExecutorFactory.setIdempotentKeyParser(idempotentKeyParser);
        simpleIdempotentExecutorFactory.setSerializer(serializer);
        simpleIdempotentExecutorFactory.setExecutionRecordRepository(executionRecordRepository);
        return simpleIdempotentExecutorFactory;
    }

    @Bean
    public ExecutionRecordRepository executionRecordRepository(RedisTemplate<String, ExecutionRecord> recordRedisTemplate){
        return new RedisBasedExecutionRecordRepository("ide-%s-%s", Duration.ofDays(7), recordRedisTemplate);
    }

    @Bean
    public IdempotentKeyParser idempotentKeyParser(){
        return new SimpleIdempotentKeyParser();
    }

    @Bean
    public RedisTemplate<String, ExecutionRecord> recordRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, ExecutionRecord> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson2JsonRedisSerializer<ExecutionRecord> executionRecordJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(ExecutionRecord.class);
        executionRecordJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(executionRecordJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public ExecutionResultSerializer executionResultSerializer(){
        return new SimpleExecutionResultSerializer();
    }

}
