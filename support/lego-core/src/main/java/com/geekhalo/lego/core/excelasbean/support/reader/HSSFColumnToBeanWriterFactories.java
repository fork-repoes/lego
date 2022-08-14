package com.geekhalo.lego.core.excelasbean.support.reader;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFColumnToBeanWriterFactories {
    private final List<HSSFColumnToBeanWriterFactory> columnToBeanWriterFactories = Lists.newArrayList();

    public HSSFColumnToBeanWriterFactories(List<HSSFColumnToBeanWriterFactory> columnToBeanWriterFactories) {
        if (CollectionUtils.isNotEmpty(columnToBeanWriterFactories)) {
            this.columnToBeanWriterFactories.addAll(columnToBeanWriterFactories);
        }
        this.columnToBeanWriterFactories.add(new HSSFEmbeddedColumnToBeanWriterFactory(this));

    }


    public <D> List<HSSFColumnToBeanWriter> create(String parentPath, Class<D> cls) {
        return this.columnToBeanWriterFactories.stream()
                .flatMap(factory -> factory.create(parentPath, cls).stream())
                .collect(toList());
    }
}
