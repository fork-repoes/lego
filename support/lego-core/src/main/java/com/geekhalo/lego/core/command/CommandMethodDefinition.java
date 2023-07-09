package com.geekhalo.lego.core.command;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandMethodDefinition {
    Class contextClass();
}
