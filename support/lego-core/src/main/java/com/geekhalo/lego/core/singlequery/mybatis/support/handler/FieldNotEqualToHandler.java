package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldNotEqualTo;

public class FieldNotEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldNotEqualTo>
    implements FilterAnnotationHandler<FieldNotEqualTo>{
    public FieldNotEqualToHandler() {
        super("NotEqualTo");
    }

    @Override
    public String getFieldValue(FieldNotEqualTo annotation) {
        return annotation.value();
    }
}
