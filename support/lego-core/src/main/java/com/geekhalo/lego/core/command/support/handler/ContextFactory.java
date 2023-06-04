package com.geekhalo.lego.core.command.support.handler;

@FunctionalInterface
public interface ContextFactory<CMD, CONTENT> {
    CONTENT create(CMD cmd);
}
