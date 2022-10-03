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
public class QueryServiceMethodInvoker<Q, R>
        implements com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker {
    private final Function<Object[], Q> queryExecutor;
    private final Function<Q, R> converter;
    private final Function<R, R> filler;
    @Override
    public final Object invoke(Method method, Object[] arguments) {
        validate(method, arguments);
        Q qResult = query(method, arguments);
        R resultResult = convert(qResult);
        fill(resultResult);
        return resultResult;
    }

    private Q query(Method method, Object[] arguments) {
        return queryExecutor.apply(arguments);
    }

    private R convert(Q queryResult) {
        return converter.apply(queryResult);
    }

    private R fill(R queryResult) {
        return filler.apply(queryResult);
    }

    protected void validate(Method method, Object[] arguments){
        return;
    }
}
