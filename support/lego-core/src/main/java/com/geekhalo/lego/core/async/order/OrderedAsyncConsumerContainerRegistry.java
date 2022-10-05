package com.geekhalo.lego.core.async.order;

import com.geekhalo.lego.annotation.async.AsyncForOrderedBasedRocketMQ;
import com.geekhalo.lego.core.support.consumer.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by taoli on 2022/9/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class OrderedAsyncConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public OrderedAsyncConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object proxy, String s) throws BeansException {
        // 1. 获取 @AsyncForOrderedBasedRocketMQ 注解方法
        Class targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, AsyncForOrderedBasedRocketMQ.class);

        // 2. 为每个 @AsyncForOrderedBasedRocketMQ 注解方法 注册 OrderedAsyncConsumerContainer
        for(Method method : methodsListWithAnnotation){
            AsyncForOrderedBasedRocketMQ annotation = method.getAnnotation(AsyncForOrderedBasedRocketMQ.class);
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)){
                continue;
            }

            Object bean = AopProxyUtils.getSingletonTarget(proxy);
            OrderedAsyncConsumerContainer asyncConsumerContainer =
                    new OrderedAsyncConsumerContainer(this.getEnvironment(),
                            annotation,
                            bean,
                            method);
            asyncConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(asyncConsumerContainer);
        }
        return proxy;
    }
}
