package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldIsNull;

public class FieldIsNullHandler extends AbstractFilterAnnotationHandler<FieldIsNull>
    implements FilterAnnotationHandler<FieldIsNull>{
    public FieldIsNullHandler() {
        super("IsNull");
    }

    @Override
    public String getFieldValue(FieldIsNull annotation) {
        return annotation.value();
    }

    @Override
    public boolean hasParam() {
        return false;
    }
}
