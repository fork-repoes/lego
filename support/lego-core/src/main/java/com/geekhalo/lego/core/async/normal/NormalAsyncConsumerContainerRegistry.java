package com.geekhalo.lego.core.async.normal;

import com.geekhalo.lego.annotation.async.AsyncBasedRocketMQ;
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
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 基于 BeanPostProcessor#postProcessAfterInitialization 对每个 bean 进行处理
 * 扫描 bean 中被 @AsyncBasedRocketMQ 注解的方法，并将方法封装成 AsyncConsumerContainer，
 * 以启动 DefaultMQPushConsumer
 */
public class NormalAsyncConsumerContainerRegistry
        extends AbstractConsumerContainerRegistry {


    public NormalAsyncConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    /**
     * 对每个 bean 依次进行处理
     * @param proxy
     * @param beanName
     * @return
     * @throws BeansException
     */
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object proxy, String beanName) throws BeansException {
        // 1. 获取 @AsyncBasedRocketMQ 注解方法
        Class targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, AsyncBasedRocketMQ.class);

        // 2. 为每个 @AsyncBasedRocketMQ 注解方法 注册 NormalAsyncConsumerContainer
        for(Method method : methodsListWithAnnotation){
            AsyncBasedRocketMQ annotation = method.getAnnotation(AsyncBasedRocketMQ.class);

            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)){
                continue;
            }

            Object bean = AopProxyUtils.getSingletonTarget(proxy);
            NormalAsyncConsumerContainer asyncConsumerContainer = new NormalAsyncConsumerContainer(this.getEnvironment(), annotation, bean, method);
            asyncConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(asyncConsumerContainer);
        }

        return proxy;
    }
}
