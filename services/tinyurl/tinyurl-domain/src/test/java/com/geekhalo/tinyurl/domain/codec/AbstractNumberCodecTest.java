package com.geekhalo.tinyurl.domain.codec;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractNumberCodecTest {
    private NumberCodec numberCodec;

    @BeforeEach
    public void init() {
        this.numberCodec = createNumberCodec();
    }

    @Test
    void encode() {
        for (int i = 0; i < 10000; i++) {
            Long number = RandomUtils.nextLong(0, Long.MAX_VALUE);
            String code = this.numberCodec.encode(number);
            Long decodeNumber = this.numberCodec.decode(code);
            Assert.assertEquals(number, decodeNumber);
            System.out.println(number + ":" + code);
        }
    }

    abstract NumberCodec createNumberCodec();
}
