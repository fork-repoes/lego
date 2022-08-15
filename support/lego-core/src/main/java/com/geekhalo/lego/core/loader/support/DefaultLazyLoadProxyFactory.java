package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import org.springframework.aop.framework.ProxyFactory;


public class DefaultLazyLoadProxyFactory
        extends AbstractLazyLoadProxyFactory
        implements LazyLoadProxyFactory {
    private final PropertyLazyLoaderFactory propertyLazyLoaderFactory;
    public DefaultLazyLoadProxyFactory(PropertyLazyLoaderFactory propertyLazyLoaderFactory) {
        this.propertyLazyLoaderFactory = propertyLazyLoaderFactory;
    }

    @Override
    protected <T> T createProxyFor(Class cls, T t) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(t);
        proxyFactory.addAdvice(this.propertyLazyLoaderFactory.createFor(cls, t));
        return (T)proxyFactory.getProxy();
    }
}