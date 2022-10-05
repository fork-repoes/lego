package com.geekhalo.lego.core.support.invoker;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class TargetBasedServiceMethodInvoker
        implements ServiceMethodInvoker {
    private final Object target;
    private final Method targetMethod;

    public TargetBasedServiceMethodInvoker(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
    }

    @Override
    public Object invoke(Method method, Object[] arguments) {
        return ReflectionUtils.invokeMethod(targetMethod, this.target, arguments);
    }
}
