package com.geekhalo.lego.core.loader.support;


import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;

abstract class AbstractLazyLoadProxyFactory implements LazyLoadProxyFactory {
    @Override
    public <T> T createProxyFor(T t) {
        if (t == null){
            return null;
        }
        Class cls = t.getClass();
        return createProxyFor(cls, t);
    }

    protected abstract <T> T createProxyFor(Class cls, T t);
}
