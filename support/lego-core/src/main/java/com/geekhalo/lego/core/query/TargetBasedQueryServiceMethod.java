package com.geekhalo.lego.core.query;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class TargetBasedQueryServiceMethod
        extends AbstractQueryServiceMethod<Object, Object>
        implements QueryServiceMethod{
    private final Object target;
    private final Method targetMethod;

    public TargetBasedQueryServiceMethod(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
    }

    @Override
    protected Object query(Method method, Object[] arguments) {
        return ReflectionUtils.invokeMethod(targetMethod, this.target, arguments);
    }

    @Override
    protected Object convert(Object queryResult) {
        return queryResult;
    }

    @Override
    protected Object fill(Object queryResult) {
        return queryResult;
    }
}
