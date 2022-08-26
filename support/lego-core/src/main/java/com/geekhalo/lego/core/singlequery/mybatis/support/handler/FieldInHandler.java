package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldIn;

public class FieldInHandler extends AbstractFilterAnnotationHandler<FieldIn>
    implements FieldAnnotationHandler<FieldIn>{
    public FieldInHandler() {
        super(FieldIn.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldIn notEqualTo, Object value) throws Exception{
        addCriteria(criteria, notEqualTo.value(), "In", value);
    }
}
