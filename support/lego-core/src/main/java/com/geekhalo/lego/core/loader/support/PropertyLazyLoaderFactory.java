package com.geekhalo.lego.core.loader.support;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class PropertyLazyLoaderFactory {
    private final ApplicationContext applicationContext;

    public PropertyLazyLoaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<PropertyLazyLoader> createFor(Class cls) {
        return FieldUtils.getAllFieldsList(cls).stream()
                .map(this::createFromField)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PropertyLazyLoader createFromField(Field field){
        LazyLoadBy mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, LazyLoadBy.class);
        if (mergedAnnotation == null){
            return null;
        }

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

        return new PropertyLazyLoader(field, new BeanFactoryResolver(this.applicationContext), targetEl);
    }
}
