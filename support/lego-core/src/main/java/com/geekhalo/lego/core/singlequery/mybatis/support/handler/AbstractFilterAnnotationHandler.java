package com.geekhalo.lego.core.singlequery.mybatis.support.handler;

import java.lang.annotation.Annotation;

abstract class AbstractFilterAnnotationHandler<A extends Annotation>
        implements FilterAnnotationHandler<A>{
    private final String operator;

    protected AbstractFilterAnnotationHandler(String operator) {
        this.operator = operator;
    }

    @Override
    public final String getOperator() {
        return operator;
    }
}
