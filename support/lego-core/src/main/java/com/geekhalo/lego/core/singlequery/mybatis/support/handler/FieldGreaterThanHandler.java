package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldGreaterThan;

public class FieldGreaterThanHandler
        extends AbstractFilterAnnotationHandler<FieldGreaterThan>
        implements FilterAnnotationHandler<FieldGreaterThan> {
    public FieldGreaterThanHandler() {
        super("GreaterThan");
    }

    @Override
    public String getFieldValue(FieldGreaterThan annotation) {
        return annotation.value();
    }

}
