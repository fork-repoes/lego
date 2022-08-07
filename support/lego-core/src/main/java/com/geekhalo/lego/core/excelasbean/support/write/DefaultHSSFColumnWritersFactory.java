package com.geekhalo.lego.core.excelasbean.support.write;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFColumnWritersFactory implements HSSFColumnWritersFactory{
    private final HSSFColumnWriterFactory factory;

    public DefaultHSSFColumnWritersFactory(HSSFColumnWriterFactory factory) {
        this.factory = factory;
    }

    @Override
    public HSSFColumnWriters createFor(Class cls) {
        List<HSSFColumnWriter> columnWriters = FieldUtils.getAllFieldsList(cls).stream()
                .map(field -> this.factory.create(field))
                .filter(Objects::nonNull)
                .collect(toList());

        return new HSSFColumnWriters(columnWriters);
    }
}
