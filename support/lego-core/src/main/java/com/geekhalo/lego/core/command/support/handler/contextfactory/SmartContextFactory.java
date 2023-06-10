package com.geekhalo.lego.core.command.support.handler.contextfactory;

import com.geekhalo.lego.core.command.support.handler.contextfactory.ContextFactory;

public interface SmartContextFactory<CMD, CONTEXT> extends ContextFactory<CMD, CONTEXT> {
    boolean apply(Class cmdClass, Class contextClass);
}
