package com.geekhalo.lego.core.threadpool;

import com.alibaba.ttl.spi.TtlWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorUpdaterProcessor implements BeanPostProcessor {
    @Autowired
    private ThreadPoolExecutorUpdater threadPoolExecutorUpdater;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object realBean = bean;
        if (bean instanceof TtlWrapper){
            realBean = ((TtlWrapper) bean).unwrap();
        }
        registryForBean(beanName, realBean);
        return bean;
    }

    private void registryForBean(String beanName, Object bean) {
        if (bean instanceof ThreadPoolExecutor){
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) bean;
            this.threadPoolExecutorUpdater.registry(beanName, threadPoolExecutor);
        }
    }
}
