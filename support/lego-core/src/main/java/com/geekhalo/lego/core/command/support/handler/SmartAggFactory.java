package com.geekhalo.lego.core.command.support.handler;

public interface SmartAggFactory<CONTEXT, AGG>
        extends AggFactory<CONTEXT, AGG>{
    boolean apply(Class<CONTEXT> contextClass, Class<AGG> aggClass);
}
