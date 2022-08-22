package com.geekhalo.lego.annotation.singlequery;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Field
public @interface FieldEqualTo {
    @AliasFor(annotation = Field.class, attribute = "fieldName")
    String value();
}
