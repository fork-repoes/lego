package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldGreaterThan;
import com.geekhalo.lego.annotation.singlequery.FieldGreaterThanOrEqualTo;

public class FieldGreaterThanHandler
        extends AbstractFilterAnnotationHandler<FieldGreaterThan>
        implements FieldAnnotationHandler<FieldGreaterThan> {
    public FieldGreaterThanHandler() {
        super(FieldGreaterThan.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldGreaterThan greaterThan, Object value) throws Exception{
        addCriteria(criteria, greaterThan.value(), "GreaterThan", value);
    }

}
