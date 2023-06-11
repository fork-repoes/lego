package com.geekhalo.lego.core.query.support.handler.executor;

public interface QueryExecutor<Q> {
    Q query(Object[] params);
}
