package com.geekhalo.relation.domain.group.loader;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.geekhalo.relation.domain.group.loader.LazyLoadGroupById.BEAN_NAME;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@"+ BEAN_NAME +".loadById(${id})}")
public @interface LazyLoadGroupById {
    String BEAN_NAME = "relationGroupLoader";

    String id();
}
