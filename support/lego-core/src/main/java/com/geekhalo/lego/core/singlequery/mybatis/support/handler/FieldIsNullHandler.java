package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldIsNull;

public class FieldIsNullHandler extends AbstractFilterAnnotationHandler<FieldIsNull>
    implements FieldAnnotationHandler<FieldIsNull>{
    public FieldIsNullHandler() {
        super(FieldIsNull.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldIsNull isNull, Object value) throws Exception{
        if (value instanceof Boolean) {
            if (((Boolean) value).booleanValue()) {
                addCriteria(criteria, isNull.value(), "IsNull");
            }else {
                addCriteria(criteria, isNull.value(), "IsNotNull");
            }
        }
    }
}
