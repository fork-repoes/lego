package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.TestApplication;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedSingleNumberGenerator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("redis-single")
@Getter
class RedisBasedSingleNumberGeneratorTest extends AbstractNumberGeneratorTest{

    @Autowired
    private RedisBasedSingleNumberGenerator redisBasedNumberGenerator;

    @Override
    NumberGenerator getNumberGenerator() {
        return redisBasedNumberGenerator;
    }
}