package com.geekhalo.tinyurl.domain.codec;

import org.springframework.stereotype.Component;

@Component
public class TestNumberCodec implements NumberCodec{
    @Override
    public String code(Long number) {
        return String.valueOf(number);
    }

    @Override
    public Long decode(String code) {
        return Long.valueOf(code);
    }
}
