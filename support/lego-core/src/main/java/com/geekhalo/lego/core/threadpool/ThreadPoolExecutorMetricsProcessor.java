package com.geekhalo.lego.core.threadpool;


import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorMetricsProcessor
    extends AbstractMetricsProcessor {

    @Override
    protected void registryForBean(String beanName, Object bean) {
        if (bean instanceof ThreadPoolExecutor){
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) bean;
            getMeterRegistry().gauge(beanName + ".thread.pool.size", threadPoolExecutor, ThreadPoolExecutor::getPoolSize);
            getMeterRegistry().gauge(beanName + ".thread.pool.active.count", threadPoolExecutor, ThreadPoolExecutor::getActiveCount);
            getMeterRegistry().gauge(beanName + ".thread.pool.completed.count", threadPoolExecutor, ThreadPoolExecutor::getCompletedTaskCount);
            getMeterRegistry().gauge(beanName + ".thread.pool.queue.size", threadPoolExecutor, executor -> executor.getQueue().size());
            getMeterRegistry().gauge(beanName + ".thread.pool.queue.remaining.capacity", threadPoolExecutor, executor -> executor.getQueue().remainingCapacity());
        }
    }
}
