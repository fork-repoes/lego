package com.geekhalo.lego.core.command.support.handler;

import lombok.Value;

@Value
public class ContextFactoryNotFoundException extends RuntimeException{
    private Class cmdClass;
    private Class contextClass;
}
