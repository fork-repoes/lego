package com.geekhalo.lego.core.query.support.handler.converter;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.ConstructorUtils;

public class ConstructorBasedConvertor<P, R> implements ResultConverter<P, R>{
    private final Class staticClass;

    public ConstructorBasedConvertor(Class staticClass) {
        this.staticClass = staticClass;
    }

    @SneakyThrows
    @Override
    public R converter(P param) {
        return (R) ConstructorUtils.invokeConstructor(staticClass, param);
    }
}
