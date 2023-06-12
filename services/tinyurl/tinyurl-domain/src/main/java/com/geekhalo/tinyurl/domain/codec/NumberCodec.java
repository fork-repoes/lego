package com.geekhalo.tinyurl.domain.codec;

public interface NumberCodec {
    String code(Long number);

    Long decode(String code);
}
