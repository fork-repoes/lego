package com.geekhalo.lego.core.command.support.handler.contextfactory;

@FunctionalInterface
public interface ContextFactory<CMD, CONTENT> {
    CONTENT create(CMD cmd);
}
