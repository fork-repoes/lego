package com.geekhalo.lego.core.command.support.handler;

public interface SmartContextFactory<CMD, CONTEXT> extends ContextFactory<CMD, CONTEXT> {
    boolean apply(Class cmdClass, Class contextClass);
}
