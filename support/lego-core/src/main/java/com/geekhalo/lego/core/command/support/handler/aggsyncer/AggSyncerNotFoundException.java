package com.geekhalo.lego.core.command.support.handler.aggsyncer;

import lombok.Value;

@Value
public class AggSyncerNotFoundException extends RuntimeException{
    private final Class aggClass;
}
