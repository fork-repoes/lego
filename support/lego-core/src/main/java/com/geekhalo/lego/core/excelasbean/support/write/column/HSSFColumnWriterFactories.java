package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFColumnWriterFactories {
    private final List<HSSFColumnWriterFactory> writerFactories = Lists.newArrayList();
    public HSSFColumnWriterFactories(HSSFColumnOrderProviders orderProviders, HSSFColumnWriterFactory writerFactory){
        Preconditions.checkArgument(writerFactory != null);
        this.writerFactories.add(writerFactory);
        this.writerFactories.add(new HSSFEmbeddedColumWriterFactory(orderProviders, this));

    }
    public List<HSSFColumnWriter> createForCls(Class cls){
        List<HSSFColumnWriter> writers = Lists.newArrayList();

        this.writerFactories.forEach(columnWriterFactory -> {

            List<HSSFColumnWriter<Object>> writerFromMethod = Stream.of(ReflectionUtils.getAllDeclaredMethods(cls))
                    .filter(method -> columnWriterFactory.support(method))
                    .map(method -> columnWriterFactory.createForGetter(method))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            writers.addAll(writerFromMethod);

            List<HSSFColumnWriter<Object>> writerFromField = FieldUtils.getAllFieldsList(cls).stream()
                    .filter(field -> columnWriterFactory.support(field))
                    .map(field -> columnWriterFactory.createForField(field))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            writers.addAll(writerFromField);
        });
        return writers;
    }
}
