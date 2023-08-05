package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.TestApplication;
import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedBatchNumberGenerator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("redis-batch")
@Getter
class RedisBasedBatchNumberGeneratorTest extends AbstractNumberGeneratorTest{

    @Autowired
    private RedisBasedBatchNumberGenerator redisBasedBatchNumberGenerator;

    @Override
    NumberGenerator getNumberGenerator() {
        return redisBasedBatchNumberGenerator;
    }
}