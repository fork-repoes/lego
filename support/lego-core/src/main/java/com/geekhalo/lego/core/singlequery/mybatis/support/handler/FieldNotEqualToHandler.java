package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldNotEqualTo;
import com.geekhalo.lego.annotation.singlequery.FieldNotIn;

public class FieldNotEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldNotEqualTo>
    implements FieldAnnotationHandler<FieldNotEqualTo>{
    public FieldNotEqualToHandler() {
        super(FieldNotEqualTo.class);
    }



    @Override
    public void addCriteria(Object criteria, FieldNotEqualTo notEqualTo, Object value) throws Exception{
        addCriteria(criteria, notEqualTo.value(), "NotEqualTo", value);
    }
}
