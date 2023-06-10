package com.geekhalo.lego.core.command.support.handler.aggfactory;

import com.geekhalo.lego.core.command.support.handler.aggfactory.AggFactory;

public interface SmartAggFactory<CONTEXT, AGG>
        extends AggFactory<CONTEXT, AGG> {
    boolean apply(Class<CONTEXT> contextClass, Class<AGG> aggClass);
}
