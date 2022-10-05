package com.geekhalo.lego.core.support.intercepter;

import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
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
public final class MethodDispatcherInterceptor implements MethodInterceptor {
    protected final Map<Method, ServiceMethodInvoker> methodMap = Maps.newHashMap();

    public boolean support(Method method){
        return methodMap.containsKey(method);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method callMethod = invocation.getMethod();
        ServiceMethodInvoker serviceMethodInvoker = this.methodMap.get(callMethod);
        if (serviceMethodInvoker != null){
            return serviceMethodInvoker.invoke(invocation.getMethod(), invocation.getArguments());
        }
        return invocation.proceed();
    }

    public void register(Method callMethod, ServiceMethodInvoker executorMethod){
        this.methodMap.put(callMethod, executorMethod);
    }
}
