package com.geekhalo.lego.core.singlequery.mybatis.support.handler;


import com.geekhalo.lego.annotation.singlequery.FieldGreaterThanOrEqualTo;

public class FieldGreaterThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldGreaterThanOrEqualTo>
    implements FilterAnnotationHandler<FieldGreaterThanOrEqualTo>{
    public FieldGreaterThanOrEqualToHandler() {
        super("GreaterThanOrEqualTo");
    }

    @Override
    public String getFieldValue(FieldGreaterThanOrEqualTo annotation) {
        return annotation.value();
    }
}
