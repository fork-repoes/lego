package com.geekhalo.lego.core.threadpool;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

abstract class AbstractMetricsProcessor implements BeanPostProcessor {
    @Autowired
    private MeterRegistry meterRegistry;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        registryForBean(beanName, bean);
        return bean;
    }

    protected MeterRegistry getMeterRegistry(){
        return meterRegistry;
    }

    protected abstract void registryForBean(String beanName, Object bean) ;

}
