package com.geekhalo.lego.core.query.support.method;

import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractQueryServiceMethodInvoker<Q,R> implements ServiceMethodInvoker {
    @Override
    public final Object invoke(Method method, Object[] arguments) {
        validate(method, arguments);
        Q qResult = query(method, arguments);
        R resultResult = convert(qResult);
        fill(resultResult);
        return resultResult;
    }

    protected void validate(Method method, Object[] arguments){
        return;
    }

    protected abstract Q query(Method method, Object[] arguments);

    protected abstract R convert(Q queryResult);

    protected abstract R fill(R queryResult);
}
