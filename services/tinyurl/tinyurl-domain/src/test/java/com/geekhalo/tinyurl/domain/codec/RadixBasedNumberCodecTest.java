package com.geekhalo.tinyurl.domain.codec;

class RadixBasedNumberCodecTest extends AbstractNumberCodecTest {


    @Override
    NumberCodec createNumberCodec() {
        return new RadixBasedNumberCodec(30);
    }
}