package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldLessThan;

public class FieldLessThanHandler
    extends AbstractFilterAnnotationHandler<FieldLessThan>
    implements FilterAnnotationHandler<FieldLessThan>{

    public FieldLessThanHandler() {
        super("LessThan");
    }

    @Override
    public String getFieldValue(FieldLessThan annotation) {
        return annotation.value();
    }
}
