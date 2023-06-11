package com.geekhalo.lego.core.command.support.handler.aggfactory;

import com.google.common.base.Preconditions;

abstract class AbstractSmartAggFactory implements SmartAggFactory {
    protected final Class contextClass;
    protected final Class aggClass;

    AbstractSmartAggFactory(Class contextClass, Class aggClass) {
        Preconditions.checkArgument(contextClass != null);
        Preconditions.checkArgument(aggClass != null);
        this.contextClass = contextClass;
        this.aggClass = aggClass;
    }

    @Override
    public boolean apply(Class contextClass, Class aggClass) {
        return this.contextClass.equals(contextClass) && this.aggClass.equals(aggClass);

    }
}
