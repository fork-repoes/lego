package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfiguratorFactories;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class HSSFEmbeddedColumWriterFactory implements HSSFColumnWriterFactory{
    private final HSSFColumnOrderProviders orderProviders;
    private final HSSFColumnWriterFactories writerFactories;
    private final HSSFCellConfiguratorFactories cellConfiguratorFactories;


    public HSSFEmbeddedColumWriterFactory(HSSFColumnOrderProviders orderProviders,
                                          HSSFColumnWriterFactories writerFactories,
                                          HSSFCellConfiguratorFactories cellConfiguratorFactories) {
        this.orderProviders = orderProviders;
        this.writerFactories = writerFactories;
        this.cellConfiguratorFactories = cellConfiguratorFactories;
    }

    @Override
    public boolean support(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, HSSFEmbedded.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForGetter(Method getter) {
        Class<?> returnType = getter.getReturnType();
        List<HSSFCellConfigurator> headerCellConfigurators = this.cellConfiguratorFactories.createForHeader(getter);
        List<HSSFCellConfigurator> dataCellConfigurators = this.cellConfiguratorFactories.createForData(getter);

        List<HSSFColumnWriter> writers = this.writerFactories.createForCls(returnType);

        writers.stream()
                .filter(writer -> writer instanceof DefaultHSSFColumnWriter)
                .map(writer -> (DefaultHSSFColumnWriter) writer)
                .forEach(writer->{
                    writer.addDataCellConfigurators(dataCellConfigurators);
                    writer.addHeaderCellConfigurators(headerCellConfigurators);
                });
        int order = this.orderProviders.orderFor(getter);
        Function converter = new ConverterForMethod(getter.getName());
        return new HSSFColumnWriterComposite(writers, converter, order);
    }

    @Override
    public boolean support(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, HSSFEmbedded.class);
    }

    @Override
    public <D> HSSFColumnWriter<D> createForField(Field field) {
        Class<?> type = field.getType();
        List<HSSFColumnWriter> writers = this.writerFactories.createForCls(type);

        List<HSSFCellConfigurator> headerCellConfigurators = this.cellConfiguratorFactories.createForHeader(field);
        List<HSSFCellConfigurator> dataCellConfigurators = this.cellConfiguratorFactories.createForData(field);

        writers.stream()
                .filter(writer -> writer instanceof DefaultHSSFColumnWriter)
                .map(writer -> (DefaultHSSFColumnWriter) writer)
                .forEach(writer->{
                    writer.addDataCellConfigurators(dataCellConfigurators);
                    writer.addHeaderCellConfigurators(headerCellConfigurators);
                });

        int order = this.orderProviders.orderFor(field);
        Function converter = new ConvertForField(field.getName());
        return new HSSFColumnWriterComposite(writers, converter, order);
    }

    class ConverterForMethod implements Function{
        private final String methodName;

        ConverterForMethod(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Object apply(Object o) {
            try {
                return MethodUtils.invokeMethod(o, true, methodName);
            } catch (Exception e) {
                log.error("Filed to invoke method {} on {}", methodName, o);
            }
            return null;
        }
    }

    class ConvertForField implements Function{
        private final String fieldName;

        ConvertForField(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public Object apply(Object o) {
            try {
                return FieldUtils.readField(o, this.fieldName, true);
            } catch (IllegalAccessException e) {
                log.error("Filed to read from field {} on {}", fieldName, o);
            }
            return null;
        }
    }
}
