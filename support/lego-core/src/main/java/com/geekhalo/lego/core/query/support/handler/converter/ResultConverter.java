package com.geekhalo.lego.core.query.support.handler.converter;

public interface ResultConverter<P, R> {
    R convert(P param);
}
