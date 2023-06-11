package com.geekhalo.lego.core.query.support.handler.converter;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Constructor;

public class ConstructorBasedConvertor<P, R> implements ResultConverter<P, R>{
    private final Class staticClass;
    private final Constructor constructor;

    public ConstructorBasedConvertor(Class staticClass, Constructor constructor) {
        this.staticClass = staticClass;
        this.constructor = constructor;
    }

    @SneakyThrows
    @Override
    public R convert(P param) {
        return (R) ConstructorUtils.invokeConstructor(staticClass, param);
    }

    @Override
    public String toString() {
        return constructor.toString();
    }
}
