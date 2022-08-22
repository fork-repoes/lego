package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldLessThanOrEqualTo;

public class FieldLessThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldLessThanOrEqualTo>
    implements FilterAnnotationHandler<FieldLessThanOrEqualTo>{
    public FieldLessThanOrEqualToHandler() {
        super("LessThanOrEqualTo");
    }

    @Override
    public String getFieldValue(FieldLessThanOrEqualTo annotation) {
        return annotation.value();
    }
}
