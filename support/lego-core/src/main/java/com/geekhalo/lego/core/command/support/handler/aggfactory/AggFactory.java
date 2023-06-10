package com.geekhalo.lego.core.command.support.handler.aggfactory;

@FunctionalInterface
public interface AggFactory<CONTEXT, AGG> {
    AGG create(CONTEXT context);
}
