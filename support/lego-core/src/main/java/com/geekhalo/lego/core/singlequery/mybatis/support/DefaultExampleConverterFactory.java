package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverter;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverterFactory;
import com.geekhalo.lego.core.singlequery.mybatis.support.handler.FieldAnnotationHandler;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultExampleConverterFactory implements ExampleConverterFactory {
    private final List<FieldAnnotationHandler> fieldAnnotationHandlers;

    public DefaultExampleConverterFactory(List<FieldAnnotationHandler> fieldAnnotationHandlers) {
        Preconditions.checkArgument(fieldAnnotationHandlers != null);
        this.fieldAnnotationHandlers = fieldAnnotationHandlers;
    }

    @Override
    public ExampleConverter createFor(Class example) {
        return new ReflectBasedExampleConverter(example, this.fieldAnnotationHandlers);
    }
}
