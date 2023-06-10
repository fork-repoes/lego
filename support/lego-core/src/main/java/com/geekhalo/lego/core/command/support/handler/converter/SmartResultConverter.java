package com.geekhalo.lego.core.command.support.handler.converter;

import com.geekhalo.lego.core.command.support.handler.converter.ResultConverter;

public interface SmartResultConverter<AGG, CONTEXT, RESULT>
    extends ResultConverter<AGG, CONTEXT, RESULT> {

    boolean apply(Class aggClass, Class contextClass, Class resultClass);
}
