package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import org.springframework.context.ApplicationContext;

public class AutowiredLazyLoadProxyFactoryWrapper implements LazyLoadProxyFactory {
    private final LazyLoadProxyFactory lazyLoadProxyFactory;
    private final ApplicationContext applicationContext;

    AutowiredLazyLoadProxyFactoryWrapper(LazyLoadProxyFactory lazyLoadProxyFactory,
                                         ApplicationContext applicationContext) {
        this.lazyLoadProxyFactory = lazyLoadProxyFactory;
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T createProxyFor(T t) {
        if (t != null){
            applicationContext.getAutowireCapableBeanFactory()
                    .autowireBean(t);
        }
        return lazyLoadProxyFactory.createProxyFor(t);
    }
}