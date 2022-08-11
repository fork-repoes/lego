package com.geekhalo.lego.core.excelasbean.support.write.row;

import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriter;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFRowWriterFactory implements HSSFRowWriterFactory{
    private final HSSFColumnWriterFactory columnWriterFactory;

    public DefaultHSSFRowWriterFactory(HSSFColumnWriterFactory columnWriterFactory) {
        this.columnWriterFactory = columnWriterFactory;
    }

    @Override
    public <D> HSSFRowWriter<D> create(Class<D> cls) {
        List<HSSFColumnWriter<Object>> writers = Lists.newArrayList();

        List<HSSFColumnWriter<Object>> writerFromMethod = Stream.of(ReflectionUtils.getAllDeclaredMethods(cls))
                .map(method -> columnWriterFactory.createForGetter(method))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        writers.addAll(writerFromMethod);

        List<HSSFColumnWriter<Object>> writerFromField = FieldUtils.getAllFieldsList(cls).stream()
                .map(field -> columnWriterFactory.createForField(field))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        writers.addAll(writerFromField);

        return new DefaultHSSFRowWriter(writers);
    }

}
