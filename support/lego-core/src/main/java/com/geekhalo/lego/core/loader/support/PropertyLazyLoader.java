package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;
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
import java.util.Map;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.getPropertyName;
import static ch.qos.logback.core.joran.util.beans.BeanUtil.isGetter;

public class PropertyLazyLoader implements InvocationHandler, MethodInterceptor {
    private final ApplicationContext applicationContext;
    private final Object target;

    public PropertyLazyLoader(ApplicationContext applicationContext, Object target) {
        this.applicationContext = applicationContext;
        this.target = target;
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
        if (isGetter(method)){
            Object data = method.invoke(target, objects);
            if (data != null){
                return data;
            }

            String propertyName = getPropertyName(method);
            Field field = FieldUtils.getField(target.getClass(), propertyName, true);
            data = loadData(o, field);

            if (data != null){

                FieldUtils.writeField(target, propertyName, data, true);
            }

        }
        return method.invoke(target, objects);
    }


    private Object loadData(Object o, Field field) {
        LazyLoadBy mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, LazyLoadBy.class);
        String el = mergedAnnotation.value();

        String targetEl = el;
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations){
            AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.findMergedAnnotationAttributes(field, annotation.annotationType(), true, true);
            for (Map.Entry<String, Object> entry : mergedAnnotationAttributes.entrySet()){
                String key = "${" + entry.getKey() + "}";
                String value = String.valueOf(entry.getValue());
                targetEl = targetEl.replace(key, value);
            }
        }
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        Expression expression = parser.parseExpression(targetEl, templateParserContext);
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));

        return expression.getValue(evaluationContext, o);
    }
}
