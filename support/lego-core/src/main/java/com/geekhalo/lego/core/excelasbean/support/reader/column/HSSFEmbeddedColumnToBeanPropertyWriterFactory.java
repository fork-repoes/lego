package com.geekhalo.lego.core.excelasbean.support.reader.column;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriter;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriterChain;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.DefaultBeanPropertyWriter;
import com.geekhalo.lego.core.excelasbean.support.reader.cell.HSSFCellReader;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 负责对 @HSSFEmbedded 字段的解析
 */
@Slf4j
public class HSSFEmbeddedColumnToBeanPropertyWriterFactory implements HSSFColumnToBeanPropertyWriterFactory {
    private final HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories;
    private final HSSFHeaderParser hssfHeaderParser;

    public HSSFEmbeddedColumnToBeanPropertyWriterFactory(HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories,
                                                         HSSFHeaderParser hssfHeaderParser) {
        Preconditions.checkArgument(columnToBeanWriterFactories != null);
        Preconditions.checkArgument(hssfHeaderParser != null);

        this.columnToBeanWriterFactories = columnToBeanWriterFactories;
        this.hssfHeaderParser = hssfHeaderParser;
    }

    @Override
    public <D> List<HSSFColumnToBeanPropertyWriter> create(String parentPath, Class<D> cls) {
        List<HSSFColumnToBeanPropertyWriter> columnToBeanWriters = Lists.newArrayList();
        for (Field field : FieldUtils.getFieldsListWithAnnotation(cls, HSSFEmbedded.class)){
            String path = hssfHeaderParser.toPath(parentPath, field);

            // 为 field 字段注册 HSSFColumnToBeanPropertyWriter
            HSSFColumnToBeanPropertyWriter beanWriter = buildBeanWriter(path, field);
            columnToBeanWriters.add(beanWriter);

            // 为 field 解析结果注册 HSSFColumnToBeanPropertyWriter
            List<HSSFColumnToBeanPropertyWriter> columnToBeanWriterList = this.columnToBeanWriterFactories.create(path, field.getType());
            columnToBeanWriters.addAll(columnToBeanWriterList);
        }
        return columnToBeanWriters;
    }

    private HSSFColumnToBeanPropertyWriter buildBeanWriter(String path, Field field) {

        // 自动创建 Embedded 对象
        HSSFCellReader cellReader = cell -> {
            try {
                return ConstructorUtils.invokeConstructor(field.getType());
            } catch (Exception e) {
                log.error("Failed to create bean {}", field.getType(), e);
            }
            return null;
        };

        BeanPropertyWriter beanWriter = new DefaultBeanPropertyWriter(path);

        BeanPropertyWriterChain writerChain = new BeanPropertyWriterChain(cellReader,  beanWriter);

        return new HSSFColumnToBeanPropertyWriter(path, null,  writerChain);
    }
}
