package com.geekhalo.lego.core.query;

import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by taoli on 2022/9/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 将请求路由到 target 对象
 */
public class TargetMethodExecutionInterceptor implements MethodInterceptor {
    private final Object target;
    private final Map<Method, Method> methodMapping = Maps.newHashMap();

    public TargetMethodExecutionInterceptor(Object target) {
        this.target = target;
    }

    public void initMethods(Set<Method> methods){
        Class targetClass = target.getClass();
        for (Method callMethod : methods){
            Method targetMethod = MethodUtils.getMatchingAccessibleMethod(targetClass, callMethod.getName(), callMethod.getParameterTypes());
            if (targetMethod != null){
                this.methodMapping.put(callMethod, targetMethod);
            }
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method callMethod = invocation.getMethod();
        Method targetMethod = this.methodMapping.get(callMethod);
        if (targetMethod != null){
            return ReflectionUtils.invokeMethod(targetMethod, this.target, invocation.getArguments());
        }
        return invocation.proceed();
    }

    public boolean support(Method method) {
        return this.methodMapping.containsKey(method);
    }
}
