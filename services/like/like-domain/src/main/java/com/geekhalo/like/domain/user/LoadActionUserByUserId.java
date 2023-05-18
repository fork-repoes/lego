package com.geekhalo.like.domain.user;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@actionUserLoader.loadByUserId(${userId})}")
public @interface LoadActionUserByUserId {
    String userId();
}
