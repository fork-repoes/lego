package com.geekhalo.lego.core.excelasbean.support.write.cell;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellWriterFactories {
    private final List<HSSFHeaderCellWriterFactory> headerCellWriterFactories = Lists.newArrayList();
    private final List<HSSFDataCellWriterFactory> dataCellWriterFactories = Lists.newArrayList();

    public HSSFCellWriterFactories(List<HSSFHeaderCellWriterFactory> headerCellWriterFactories,
                                   List<HSSFDataCellWriterFactory> dataCellWriterFactories) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(headerCellWriterFactories));
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataCellWriterFactories));

        this.headerCellWriterFactories.addAll(headerCellWriterFactories);
        this.dataCellWriterFactories.addAll(dataCellWriterFactories);

        AnnotationAwareOrderComparator.sort(this.headerCellWriterFactories);
        AnnotationAwareOrderComparator.sort(this.dataCellWriterFactories);
    }

    public HSSFCellWriter createForHeader(AnnotatedElement element, String name) {
        return this.headerCellWriterFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element, name))
                .findFirst()
                .orElse(null);
    }

    public HSSFCellWriter createForData(AnnotatedElement element, String name) {
        return this.dataCellWriterFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element, name))
                .findFirst()
                .orElse(null);
    }
}
