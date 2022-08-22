package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldNotIn;

public class FieldNotInHandler
    extends AbstractFilterAnnotationHandler<FieldNotIn>
    implements FilterAnnotationHandler<FieldNotIn>{
    public FieldNotInHandler() {
        super("NotIn");
    }

    @Override
    public String getFieldValue(FieldNotIn annotation) {
        return annotation.value();
    }
}
