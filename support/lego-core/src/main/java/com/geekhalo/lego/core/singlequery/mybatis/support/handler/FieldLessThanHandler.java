package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldIn;
import com.geekhalo.lego.annotation.singlequery.FieldLessThan;

public class FieldLessThanHandler
    extends AbstractFilterAnnotationHandler<FieldLessThan>
    implements FieldAnnotationHandler<FieldLessThan>{

    public FieldLessThanHandler() {
        super(FieldLessThan.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldLessThan lessThan, Object value) throws Exception{
        addCriteria(criteria, lessThan.value(), "LessThan", value);
    }
}
