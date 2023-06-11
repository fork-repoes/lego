package com.geekhalo.lego.core.command.support.handler;


public interface CommandHandler<CMD, RESULT> {
    RESULT handle(CMD cmd);
}
