package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldIn;

public class FieldInHandler extends AbstractFilterAnnotationHandler<FieldIn>
    implements FilterAnnotationHandler<FieldIn>{
    public FieldInHandler() {
        super("In");
    }

    @Override
    public String getFieldValue(FieldIn annotation) {
        return annotation.value();
    }
}
