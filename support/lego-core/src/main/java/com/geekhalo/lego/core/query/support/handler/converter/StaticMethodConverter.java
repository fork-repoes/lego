package com.geekhalo.lego.core.query.support.handler.converter;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

public class StaticMethodConverter<P, R> implements ResultConverter<P, R>{
    private final Class staticClass;
    private final Method method;

    public StaticMethodConverter(Class staticClass, Method method) {
        this.staticClass = staticClass;
        this.method = method;
    }

    @SneakyThrows
    @Override
    public R converter(P param) {
        return (R) MethodUtils.invokeStaticMethod(staticClass, method.getName(), param);
    }
}
