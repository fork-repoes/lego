package com.geekhalo.lego.core.threadpool;

import com.alibaba.ttl.spi.TtlWrapper;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPoolExecutorTtlWrapperProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return wrapperForForBean(beanName, bean);
    }

    private Object wrapperForForBean(String beanName, Object bean) {
        if (bean instanceof TtlWrapper){
            return bean;
        }

        if (bean instanceof Executor){
            Executor executor = (Executor) bean;
            return TtlExecutors.getTtlExecutor(executor);
        }

        if (bean instanceof ScheduledExecutorService){
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) bean;
            return TtlExecutors.getTtlScheduledExecutorService(scheduledExecutorService);
        }

        if (bean instanceof ExecutorService){
            ExecutorService executorService = (ExecutorService) bean;
            return TtlExecutors.getTtlExecutorService(executorService);
        }
        return bean;
    }

}
