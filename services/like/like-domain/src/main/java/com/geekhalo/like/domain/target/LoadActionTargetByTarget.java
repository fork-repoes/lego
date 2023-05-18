package com.geekhalo.like.domain.target;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@ActionTargetLoader.loadByTarget(${targetType}, ${targetId)}")
public @interface LoadActionTargetByTarget {
    String targetType();
    String targetId();
}
