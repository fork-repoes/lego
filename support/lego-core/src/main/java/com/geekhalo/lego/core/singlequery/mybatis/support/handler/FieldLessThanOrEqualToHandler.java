package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldLessThanOrEqualTo;

public class FieldLessThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldLessThanOrEqualTo>
    implements FieldAnnotationHandler<FieldLessThanOrEqualTo>{

    public FieldLessThanOrEqualToHandler() {
        super(FieldLessThanOrEqualTo.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldLessThanOrEqualTo lessThanOrEqualTo, Object value) throws Exception{
        addCriteria(criteria, lessThanOrEqualTo.value(), "LessThanOrEqualTo", value);
    }
}
