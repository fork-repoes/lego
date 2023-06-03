package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.Command;

public interface CommandHandler<CMD extends Command, RESULT> {
    RESULT handle(CMD cmd);
}
