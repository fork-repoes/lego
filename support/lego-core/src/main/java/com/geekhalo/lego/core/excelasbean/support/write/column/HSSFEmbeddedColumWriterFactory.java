package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFEmbeddedColumWriterFactory implements HSSFColumnWriterFactory{
    private final HSSFColumnOrderProviders orderProviders;
    private final HSSFColumnWriterFactories writerFactories;


    public HSSFEmbeddedColumWriterFactory(HSSFColumnOrderProviders orderProviders,
                                          HSSFColumnWriterFactories writerFactories) {
        this.orderProviders = orderProviders;
        this.writerFactories = writerFactories;
    }

    @Override
    public boolean support(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, HSSFEmbedded.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForGetter(Method getter) {
        Class<?> returnType = getter.getReturnType();
        List<HSSFColumnWriter> writers = this.writerFactories.createForCls(returnType);

        int order = this.orderProviders.orderFor(getter);
        return new HSSFColumnWriterComposite(writers, "path", order);
    }

    @Override
    public boolean support(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, HSSFEmbedded.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForField(Field field) {
        Class<?> type = field.getType();
        List<HSSFColumnWriter> writers = this.writerFactories.createForCls(type);

        int order = this.orderProviders.orderFor(field);
        return new HSSFColumnWriterComposite(writers, field.getName(), order);
    }
}
