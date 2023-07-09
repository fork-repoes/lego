package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

abstract class AbstractNumberGeneratorTest {
    @Test
    public void nextNumber(){
        Set<Long> numbers = new HashSet<>();
        for (int i = 0; i < 10; i++){
            Long number = getNumberGenerator().nextNumber();
            numbers.add(number);
        }
        Assert.assertEquals(10, numbers.size());
    }

    abstract NumberGenerator getNumberGenerator();
}
