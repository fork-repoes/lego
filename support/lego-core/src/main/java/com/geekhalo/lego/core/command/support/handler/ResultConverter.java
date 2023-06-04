package com.geekhalo.lego.core.command.support.handler;

@FunctionalInterface
public interface ResultConverter<AGG, CONTEXT, RESULT> {
    RESULT convert(AGG agg, CONTEXT context);
}
