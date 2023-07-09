package com.geekhalo.tinyurl.domain.codec;

class CustomBaseNumberCodecTest extends AbstractNumberCodecTest{

    @Override
    NumberCodec createNumberCodec() {
        return new CustomBaseNumberCodec();
    }
}