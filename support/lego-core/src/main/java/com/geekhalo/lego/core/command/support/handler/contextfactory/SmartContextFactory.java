package com.geekhalo.lego.core.command.support.handler.contextfactory;

public interface SmartContextFactory<CMD, CONTEXT> extends ContextFactory<CMD, CONTEXT> {
    boolean apply(Class cmdClass, Class contextClass);
}
