package com.geekhalo.lego.core.loader.support;


import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Modifier;

abstract class AbstractLazyLoadProxyFactory implements LazyLoadProxyFactory {
    @Override
    public <T> T createProxyFor(T t) {
        if (t == null){
            return null;
        }

        // 基础类型直接返回
        Class cls = t.getClass();
        if (cls.isPrimitive() || ClassUtils.isPrimitiveWrapper(cls)){
            return t;
        }
        // 跳过 final 类
        if (Modifier.isFinal(cls.getModifiers())){
            return t;
        }

        return createProxyFor(cls, t);
    }

    protected abstract <T> T createProxyFor(Class cls, T t);
}
