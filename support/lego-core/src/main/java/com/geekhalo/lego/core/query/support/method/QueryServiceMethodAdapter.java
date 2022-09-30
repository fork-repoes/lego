package com.geekhalo.lego.core.query.support.method;

import lombok.Builder;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
@Builder
public class QueryServiceMethodAdapter<Q, R>
    extends AbstractQueryServiceMethod<Q, R>{
    private final Function<Object[], Q> queryExecutor;
    private final Function<Q, R> converter;
    private final Function<R, R> filler;

    @Override
    protected Q query(Method method, Object[] arguments) {
        return queryExecutor.apply(arguments);
    }

    @Override
    protected R convert(Q queryResult) {
        return converter.apply(queryResult);
    }

    @Override
    protected R fill(R queryResult) {
        return filler.apply(queryResult);
    }
}
