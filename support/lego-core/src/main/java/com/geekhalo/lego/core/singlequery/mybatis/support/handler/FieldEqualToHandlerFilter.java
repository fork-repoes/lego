package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;

public class FieldEqualToHandlerFilter
        extends AbstractFilterAnnotationHandler<FieldEqualTo>
        implements FilterAnnotationHandler<FieldEqualTo> {

    public FieldEqualToHandlerFilter() {
        super("EqualTo");
    }

    @Override
    public String getFieldValue(FieldEqualTo annotation) {
        return annotation.value();
    }

}
