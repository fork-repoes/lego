package com.geekhalo.lego.starter.faultrecovery.smart;

import com.geekhalo.lego.annotation.faultrecovery.smart.SmartFault;
import com.geekhalo.lego.core.faultrecovery.smart.ActionTypeProvider;
import com.geekhalo.lego.core.faultrecovery.smart.ExceptionMapProvider;
import com.geekhalo.lego.core.faultrecovery.smart.SmartFaultMethodInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ConditionalOnBean(ActionTypeProvider.class)
public class SmartFaultAutoConfiguration {



    @ConditionalOnBean(ActionTypeProvider.class)
    @Bean
    public SmartFaultMethodInterceptor smartFaultAspect(ActionTypeProvider actionTypeProvider, Optional<ExceptionMapProvider> defaultExceptionMapProvider){
        return new SmartFaultMethodInterceptor(actionTypeProvider, defaultExceptionMapProvider.orElse(null));
    }


    @Bean
    @ConditionalOnBean(SmartFaultMethodInterceptor.class)
    public PointcutAdvisor smartAdvisor(SmartFaultMethodInterceptor smartFaultMethodInterceptor) {

        AnnotationMatchingPointcut methodPointcut =
                new AnnotationMatchingPointcut(null, SmartFault.class);
        AnnotationMatchingPointcut clsPointcut = new AnnotationMatchingPointcut(SmartFault.class, true);

        Pointcut pointcut = Pointcuts.union(methodPointcut, clsPointcut);

        AbstractPointcutAdvisor advisor = new AbstractPointcutAdvisor() {
            @Override
            public Pointcut getPointcut() {
                return pointcut;
            }

            @Override
            public Advice getAdvice() {
                return smartFaultMethodInterceptor;
            }
        };
        advisor.setOrder(-1000);
        return advisor;
    }
}
