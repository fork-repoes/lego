package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.query.support.method.QueryServiceMethod;
import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
final class QueryServiceMethodDispatcherInterceptor implements MethodInterceptor {
    protected final Map<Method, QueryServiceMethod> methodMap = Maps.newHashMap();

    public boolean support(Method method){
        return methodMap.containsKey(method);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method callMethod = invocation.getMethod();
        QueryServiceMethod queryServiceMethod = this.methodMap.get(callMethod);
        if (queryServiceMethod != null){
            return queryServiceMethod.invoke(invocation.getMethod(), invocation.getArguments());
        }
        return invocation.proceed();
    }

    public void register(Method callMethod, QueryServiceMethod executorMethod){
        this.methodMap.put(callMethod, executorMethod);
    }
}
