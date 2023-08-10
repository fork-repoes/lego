package com.geekhalo.lego.core.threadpool;

import com.alibaba.ttl.spi.TtlWrapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

abstract class AbstractMetricsProcessor implements BeanPostProcessor {
    @Autowired
    private MeterRegistry meterRegistry;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object realBean = bean;
        if (bean instanceof TtlWrapper){
            realBean = ((TtlWrapper) bean).unwrap();
        }
        registryForBean(beanName, realBean);
        return bean;
    }

    protected MeterRegistry getMeterRegistry(){
        return meterRegistry;
    }

    protected abstract void registryForBean(String beanName, Object bean) ;

}
