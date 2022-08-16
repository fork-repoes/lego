package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import org.springframework.aop.framework.ProxyFactory;


public class DefaultLazyLoadProxyFactory
        extends AbstractLazyLoadProxyFactory
        implements LazyLoadProxyFactory {
    private final LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory;

    public DefaultLazyLoadProxyFactory(LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory) {
        this.lazyLoaderInterceptorFactory = lazyLoaderInterceptorFactory;
    }

    @Override
    protected <T> T createProxyFor(Class cls, T t) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(t);
        proxyFactory.addAdvice(this.lazyLoaderInterceptorFactory.createFor(cls, t));
        return (T)proxyFactory.getProxy();
    }
}