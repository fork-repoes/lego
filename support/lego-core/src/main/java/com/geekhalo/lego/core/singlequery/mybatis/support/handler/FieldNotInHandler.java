package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldLessThanOrEqualTo;
import com.geekhalo.lego.annotation.singlequery.FieldNotIn;

public class FieldNotInHandler
    extends AbstractFilterAnnotationHandler<FieldNotIn>
    implements FieldAnnotationHandler<FieldNotIn>{
    public FieldNotInHandler() {
        super(FieldNotIn.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldNotIn notIn, Object value) throws Exception{
        addCriteria(criteria, notIn.value(), "NotIn", value);
    }
}
