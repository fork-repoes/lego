package com.geekhalo.tinyurl.infra.config;

import com.geekhalo.tinyurl.infra.generator.db.DBBasedBatchNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.DBBasedSingleNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedBatchNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedSingleNumberGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class NumberGeneratorConfiguration {


    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "redis-single")
    @ConditionalOnClass(RedisTemplate.class)
    @Bean
    public RedisBasedSingleNumberGenerator redisBasedSingleNumberGenerator(){
        return new RedisBasedSingleNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "redis-batch")
    @ConditionalOnClass(RedisTemplate.class)
    @Bean
    public RedisBasedBatchNumberGenerator redisBasedBatchNumberGenerator(){
        return new RedisBasedBatchNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "db-single")
    @Bean
    public DBBasedSingleNumberGenerator dbBasedSingleNumberGenerator(){
        return new DBBasedSingleNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "db-batch")
    @Bean
    public DBBasedBatchNumberGenerator dbBasedBatchNumberGenerator(){
        return new DBBasedBatchNumberGenerator();
    }
}
