package com.geekhalo.lego.core.command.support.handler;

@FunctionalInterface
public interface AggFactory<CONTEXT, AGG> {
    AGG create(CONTEXT context);
}
