package com.geekhalo.lego.core.excelasbean.support.reader.column;

import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 *
 * 对 HSSFColumnToBeanWriterFactory 进行统一收口
 */
public class HSSFColumnToBeanPropertyWriterFactories {
    private final List<HSSFColumnToBeanPropertyWriterFactory> columnToBeanWriterFactories = Lists.newArrayList();
    private final HSSFHeaderParser headerParser;

    public HSSFColumnToBeanPropertyWriterFactories(List<HSSFColumnToBeanPropertyWriterFactory> columnToBeanWriterFactories,
                                                   HSSFHeaderParser headerParser) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(columnToBeanWriterFactories));
        Preconditions.checkArgument(headerParser != null);

        this.headerParser = headerParser;

        if (CollectionUtils.isNotEmpty(columnToBeanWriterFactories)) {
            this.columnToBeanWriterFactories.addAll(columnToBeanWriterFactories);
        }

        // 添加 对 @HSSFEmbedded 的处理
        HSSFEmbeddedColumnToBeanPropertyWriterFactory embeddedColumnToBeanWriterFactory =
                new HSSFEmbeddedColumnToBeanPropertyWriterFactory(this, this.headerParser);
        this.columnToBeanWriterFactories.add(embeddedColumnToBeanWriterFactory);

    }


    public <D> List<HSSFColumnToBeanPropertyWriter> create(String parentPath, Class<D> cls) {
        // 从所有 Factory 中收集 HSSFColumnToBeanPropertyWriter
        return this.columnToBeanWriterFactories.stream()
                .flatMap(factory -> factory.create(parentPath, cls).stream())
                .collect(toList());
    }
}
