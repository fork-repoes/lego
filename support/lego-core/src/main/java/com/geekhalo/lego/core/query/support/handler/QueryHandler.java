package com.geekhalo.lego.core.query.support.handler;

public interface QueryHandler<RESULT> {
    RESULT query(Object[] query);
}
