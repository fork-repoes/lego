package com.geekhalo.lego.faultrecovery.smart;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class ActionAspect {
    @Pointcut("@annotation(com.geekhalo.lego.faultrecovery.smart.Action)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object action(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Action annotation = methodSignature.getMethod().getAnnotation(Action.class);
        ActionContext.set(annotation.type());
        try {
            return joinPoint.proceed();
        }finally {
            ActionContext.clear();
        }
    }
}
