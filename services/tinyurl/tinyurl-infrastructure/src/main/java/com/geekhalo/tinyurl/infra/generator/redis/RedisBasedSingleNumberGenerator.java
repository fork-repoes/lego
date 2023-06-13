package com.geekhalo.tinyurl.infra.generator.redis;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;

public class RedisBasedSingleNumberGenerator
        extends AbstractRedisBasedNumberGenerator
        implements NumberGenerator {
    
    @Override
    public Long nextNumber() {
        return this.getStringRedisTemplate().opsForValue().increment(this.getGeneratorKey());
    }
}
