package com.geekhalo.lego.core.command.support.handler;

import org.springframework.stereotype.Component;

@Component
public class VoidResultConverter implements SmartResultConverter<Object, Object, Void>{
    @Override
    public Void convert(Object o, Object o2) {
        return null;
    }

    @Override
    public boolean apply(Class aggClass, Class contextClass, Class resultClass) {
        return Void.class.equals(resultClass) || Void.TYPE.equals(resultClass);
    }
}
