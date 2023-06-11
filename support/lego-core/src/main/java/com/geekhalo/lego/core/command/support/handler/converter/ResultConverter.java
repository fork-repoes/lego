package com.geekhalo.lego.core.command.support.handler.converter;

@FunctionalInterface
public interface ResultConverter<AGG, CONTEXT, RESULT> {
    RESULT convert(AGG agg, CONTEXT context);
}
