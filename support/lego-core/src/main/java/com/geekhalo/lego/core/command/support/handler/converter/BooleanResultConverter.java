package com.geekhalo.lego.core.command.support.handler.converter;

import org.springframework.stereotype.Component;

@Component
public class BooleanResultConverter implements SmartResultConverter<Object, Object, Boolean> {
    @Override
    public Boolean convert(Object o, Object o2) {
        return true;
    }

    @Override
    public boolean apply(Class aggClass, Class contextClass, Class resultClass) {
        return Boolean.class.equals(resultClass) || Boolean.TYPE.equals(resultClass);
    }
}
