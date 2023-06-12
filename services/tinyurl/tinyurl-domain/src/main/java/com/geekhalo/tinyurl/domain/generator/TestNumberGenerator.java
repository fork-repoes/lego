package com.geekhalo.tinyurl.domain.generator;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class TestNumberGenerator implements NumberGenerator{
    @Override
    public Long nextNumber() {
        return RandomUtils.nextLong(1, Long.MAX_VALUE);
    }
}
