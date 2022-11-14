package com.geekhalo.lego.core.faultrecovery.smart;

import com.geekhalo.lego.annotation.faultrecovery.smart.SmartFault;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 */

@Slf4j
@Order(-100)
public class SmartFaultMethodInterceptor implements MethodInterceptor {
    private final ActionTypeProvider actionTypeProvider;
    private final ExceptionMapProvider exceptionMapProvider;

    private Map<Method, SmartFaultExecutor> methodSmartFaultExecutorCache = Maps.newConcurrentMap();

    @Value("${smart.fault.enable:true}")
    private boolean enable;

    public SmartFaultMethodInterceptor(ActionTypeProvider actionTypeProvider,
                                       ExceptionMapProvider exceptionMapProvider) {
        Preconditions.checkArgument(actionTypeProvider != null);

        this.exceptionMapProvider = exceptionMapProvider != null ? exceptionMapProvider : ()-> Collections.<Class<? extends Throwable>, Boolean> singletonMap(Exception.class, true);
        this.actionTypeProvider = actionTypeProvider;
    }


    private SmartFaultExecutor buildSmartFaultExecutor(MethodInvocation methodInvocation){

        Method methodBySmartFault = methodInvocation.getMethod();

        SmartFault smartFault = AnnotatedElementUtils.findMergedAnnotation(methodBySmartFault, SmartFault.class);

        if (smartFault == null){
            smartFault = AnnotatedElementUtils.findMergedAnnotation(methodBySmartFault.getDeclaringClass(), SmartFault.class);
        }
        if (smartFault == null){
            smartFault = findAnnotationOnTarget(methodInvocation.getThis(), methodBySmartFault);
        }
        if (smartFault == null){
            log.warn("Smart Fault Not Found for method {}", methodBySmartFault);
            return null;
        }

        return new SmartFaultExecutor(smartFault, methodBySmartFault, this.actionTypeProvider, this.exceptionMapProvider);
    }

    private SmartFault findAnnotationOnTarget(Object target, Method method) {
        try {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            SmartFault retryable = AnnotationUtils.findAnnotation(targetMethod, SmartFault.class);
            if (retryable == null) {
                retryable = AnnotationUtils.findAnnotation(targetMethod.getDeclaringClass(), SmartFault.class);
            }

            return retryable;
        }
        catch (Exception e) {
            return null;
        }
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (!enable){
            return methodInvocation.proceed();
        }


        Method methodBySmartFault = methodInvocation.getMethod();

        SmartFaultExecutor smartFaultExecutor = this.methodSmartFaultExecutorCache
                .computeIfAbsent(methodBySmartFault, method -> buildSmartFaultExecutor(methodInvocation));
        if (smartFaultExecutor != null) {
            return smartFaultExecutor.invoke(methodInvocation);
        }

        return methodInvocation.proceed();
    }
}
