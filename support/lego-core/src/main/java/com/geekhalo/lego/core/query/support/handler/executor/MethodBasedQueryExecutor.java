package com.geekhalo.lego.core.query.support.handler.executor;

import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class MethodBasedQueryExecutor implements QueryExecutor<Object>{
    private final Object repository;
    private final Method method;

    public MethodBasedQueryExecutor(Object repository, Method method) {
        this.repository = repository;
        this.method = method;
    }

    @SneakyThrows
    @Override
    public Object query(Object[] params) {
        return method.invoke(this.repository, params);
    }
}