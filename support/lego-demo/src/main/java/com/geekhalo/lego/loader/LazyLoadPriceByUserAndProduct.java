package com.geekhalo.lego.loader;


import com.geekhalo.lego.annotation.loader.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@priceService.getByUserAndProduct(${userId}, ${productId})}")
public @interface LazyLoadPriceByUserAndProduct {

    String userId();

    String productId();
}
