package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;

public class FieldEqualToHandler
        extends AbstractFilterAnnotationHandler<FieldEqualTo>
        implements FieldAnnotationHandler<FieldEqualTo> {

    public FieldEqualToHandler() {
        super(FieldEqualTo.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldEqualTo fieldEqualTo, Object value) throws Exception{
        addCriteria(criteria, fieldEqualTo.value(), "EqualTo", value);
    }
}
