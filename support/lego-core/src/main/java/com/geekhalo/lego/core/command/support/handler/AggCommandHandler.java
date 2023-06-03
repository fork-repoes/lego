package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.Command;

public interface AggCommandHandler<
        CMD extends Command,
        RESULT> extends CommandHandler<CMD, RESULT>{
    @Override
    RESULT handle(CMD cmd);
}
