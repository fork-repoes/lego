package com.geekhalo.lego.core.excelasbean.support.reader;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFEmbeddedColumnToBeanWriterFactory implements HSSFColumnToBeanWriterFactory{
    private final HSSFColumnToBeanWriterFactories columnToBeanWriterFactories;
    private final HSSFHeaderParser hssfHeaderParser = new DefaultHSSFHeaderParser();

    public HSSFEmbeddedColumnToBeanWriterFactory(HSSFColumnToBeanWriterFactories columnToBeanWriterFactories) {
        this.columnToBeanWriterFactories = columnToBeanWriterFactories;
    }

    @Override
    public <D> List<HSSFColumnToBeanWriter> create(String parentPath, Class<D> cls) {
        List<HSSFColumnToBeanWriter> columnToBeanWriters = Lists.newArrayList();
        for (Field field : FieldUtils.getFieldsListWithAnnotation(cls, HSSFEmbedded.class)){
            String path = hssfHeaderParser.toPath(parentPath, field);

            HSSFColumnToBeanWriter beanWriter = buildBeanWriter(path, field);
            columnToBeanWriters.add(beanWriter);

            List<HSSFColumnToBeanWriter> columnToBeanWriterList = this.columnToBeanWriterFactories.create(path, field.getType());
            columnToBeanWriters.addAll(columnToBeanWriterList);
        }
        return columnToBeanWriters;
    }

    private HSSFColumnToBeanWriter buildBeanWriter(String path, Field field) {

        HSSFCellReader cellReader = cell -> {
            try {
                return ConstructorUtils.invokeConstructor(field.getType());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        };

        BeanPropertyWriter beanWriter = new DefaultBeanPropertyWriter(path);

        BeanPropertyWriterChain writerChain = new BeanPropertyWriterChain(cellReader, null, beanWriter);

        return new HSSFColumnToBeanWriter(path, null, true, writerChain);
    }
}
