package com.geekhalo.lego.core.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

@Slf4j
public class ThreadPoolExecutorValidator implements BeanPostProcessor {
    private static final String[] DEFAULT_THREAD_FACTORY = {
            "java.util.concurrent.Executors$DefaultThreadFactory"
    };
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        checkForThreadPool(bean, beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void checkForThreadPool(Object bean, String beanName) {
        if (bean instanceof ThreadPoolExecutor){
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) bean;
            ThreadFactory threadFactory = executorService.getThreadFactory();
            Class<? extends ThreadFactory> threadFactoryClass = threadFactory.getClass();
            if (isDefaultClass(threadFactoryClass)){
                log.error("Bean {} should custom thread factory", beanName);
            }


        }
    }

    private boolean isDefaultClass(Class<? extends ThreadFactory> threadFactoryClass) {
        String clsName = threadFactoryClass.getName();
        return Stream.of(DEFAULT_THREAD_FACTORY)
                .anyMatch(factoryName -> factoryName.equalsIgnoreCase(clsName));
    }
}
