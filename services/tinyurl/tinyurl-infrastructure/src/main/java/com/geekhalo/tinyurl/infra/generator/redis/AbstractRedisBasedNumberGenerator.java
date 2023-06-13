package com.geekhalo.tinyurl.infra.generator.redis;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

@Getter(AccessLevel.PROTECTED)
public class AbstractRedisBasedNumberGenerator {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${tinyurl.number.generator.redis.key:number-generator}")
    private String generatorKey;
}
