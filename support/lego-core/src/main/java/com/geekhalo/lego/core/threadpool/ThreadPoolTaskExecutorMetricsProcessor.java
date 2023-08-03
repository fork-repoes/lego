package com.geekhalo.lego.core.threadpool;


import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


public class ThreadPoolTaskExecutorMetricsProcessor
        extends AbstractMetricsProcessor
        implements BeanPostProcessor {


    @Override
    protected void registryForBean(String beanName, Object bean) {
        if (bean instanceof ThreadPoolTaskExecutor){
            ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) bean;
            getMeterRegistry().gauge(beanName + ".thread.pool.size", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getPoolSize);
            getMeterRegistry().gauge(beanName + ".thread.pool.active.count", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getActiveCount);
            getMeterRegistry().gauge(beanName + ".thread.pool.queue.size", threadPoolTaskExecutor, executor -> executor.getThreadPoolExecutor().getQueue().size());
            getMeterRegistry().gauge(beanName + ".thread.pool.queue.remaining.capacity", threadPoolTaskExecutor, executor -> executor.getThreadPoolExecutor().getQueue().remainingCapacity());
        }
    }
}