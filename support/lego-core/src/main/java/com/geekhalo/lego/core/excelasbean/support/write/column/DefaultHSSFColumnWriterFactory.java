package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterChain;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFColumnWriterFactory implements HSSFColumnWriterFactory{
    private final HSSFColumnOrderProviders orderProviders;
    private final HSSFCellWriterChainFactory writerChainFactory;

    public DefaultHSSFColumnWriterFactory(HSSFColumnOrderProviders orderProviders,
                                          HSSFCellWriterChainFactory writerChainFactory) {
        this.orderProviders = orderProviders;
        this.writerChainFactory = writerChainFactory;
    }

    @Override
    public boolean support(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, HSSFHeader.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForGetter(Method getter) {
        return createFor(getter, getter.getName());
    }

    @Override
    public boolean support(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, HSSFHeader.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForField(Field field) {
        return createFor(field, field.getName());
    }

    private <D> HSSFColumnWriter<D> createFor(AnnotatedElement element, String name) {
        int order = this.orderProviders.orderFor(element);
        HSSFCellWriterChain<Object> headerWriterChain = this.writerChainFactory.createHeaderWriterChain(element, name);
        HSSFCellWriterChain<Object> dataWriterChain = this.writerChainFactory.createDataWriterChain(element, name);

        if (headerWriterChain == null || dataWriterChain == null){
            return null;
        }
        return new DefaultHSSFColumnWriter<>(order, name, headerWriterChain, dataWriterChain);
    }
}
