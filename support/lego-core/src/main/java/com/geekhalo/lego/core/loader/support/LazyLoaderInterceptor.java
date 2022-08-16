package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;
import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.geekhalo.lego.core.utils.BeanUtil.getPropertyName;
import static com.geekhalo.lego.core.utils.BeanUtil.isGetter;


public class LazyLoaderInterceptor implements InvocationHandler, MethodInterceptor {
    private final Map<String, PropertyLazyLoader> lazyLoaderCache;
    private final Object target;

    public LazyLoaderInterceptor(Map<String, PropertyLazyLoader> lazyLoaderCache, Object target) {
        this.target = target;
        this.lazyLoaderCache = lazyLoaderCache;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation instanceof ProxyMethodInvocation){
            ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) methodInvocation;
            return invoke(proxyMethodInvocation.getProxy(), proxyMethodInvocation.getMethod(), proxyMethodInvocation.getArguments());
        }
        return invoke(methodInvocation.getThis(), methodInvocation.getMethod(), methodInvocation.getArguments());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (isGetter(method)) {
            String propertyName = getPropertyName(method);
            PropertyLazyLoader propertyLazyLoader = this.lazyLoaderCache.get(propertyName);

            if (propertyLazyLoader != null) {
                Object data = method.invoke(target, objects);
                if (data != null) {
                    return data;
                }

                data = propertyLazyLoader.loadData(o);

                if (data != null) {
                    FieldUtils.writeField(target, propertyName, data, true);
                }
                return data;
            }
        }

        return method.invoke(target, objects);
    }
}
