package com.geekhalo.lego.core.idempotent.support;

import com.geekhalo.lego.core.idempotent.IdempotentExecutor;
import com.geekhalo.lego.core.idempotent.IdempotentMeta;
import com.geekhalo.lego.core.idempotent.IdempotentMetaParser;
import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class IdempotentInterceptor
    implements MethodInterceptor {
    private final Map<Method, IdempotentExecutor> cache = Maps.newHashMap();
    private final IdempotentMetaParser parser;
    private final IdempotentExecutorFactories factories;

    public IdempotentInterceptor(IdempotentMetaParser parser,
                                 IdempotentExecutorFactories factories) {
        this.parser = parser;
        this.factories = factories;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        IdempotentExecutor executor = this.cache.computeIfAbsent(invocation.getMethod(), this::createExecutor);
        if (executor != null){
            return executor.invoke(invocation);
        }
        return invocation.proceed();
    }

    private IdempotentExecutor createExecutor(Method method) {
        IdempotentMeta meta = this.parser.parse(method);
        return factories.create(meta);
    }
}
