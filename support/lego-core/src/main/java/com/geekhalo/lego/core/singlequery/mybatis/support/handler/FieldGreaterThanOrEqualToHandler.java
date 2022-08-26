package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldGreaterThanOrEqualTo;

public class FieldGreaterThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldGreaterThanOrEqualTo>
    implements FieldAnnotationHandler<FieldGreaterThanOrEqualTo>{
    public FieldGreaterThanOrEqualToHandler() {
        super(FieldGreaterThanOrEqualTo.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldGreaterThanOrEqualTo greaterThanOrEqualTo, Object value) throws Exception{
        addCriteria(criteria, greaterThanOrEqualTo.value(), "GreaterThanOrEqualTo", value);
    }
}
