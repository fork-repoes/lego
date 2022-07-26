package com.geekhalo.lego.core.spliter.service.support.spring;


import com.geekhalo.lego.core.spliter.service.support.spring.invoker.SplitInvoker;
import com.geekhalo.lego.core.spliter.service.support.spring.invoker.SplitInvokerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;


/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 方法拦截器，将方法调用路由至 拆分逻辑
 *
 */
@Slf4j
public class SplitInterceptor implements MethodInterceptor {
    private final SplitInvokerRegistry splitService;

    public SplitInterceptor(SplitInvokerRegistry splitService) {
        this.splitService = splitService;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object target = methodInvocation.getThis();
        Object[] params = methodInvocation.getArguments();

        SplitInvoker splitInvoker = this.splitService.getByMethod(method);
        if (splitInvoker == null){
            log.warn("failed to find split invoker for method {}", method);
            return method.invoke(target, params);
        }else {
            return splitInvoker.invoke(target, params);
        }
    }
}
