package com.geekhalo.lego.core.excelasbean.support.reader.cell;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellReaderFactories {
    private final List<HSSFCellReaderFactory> cellReaderFactories = Lists.newArrayList();

    public HSSFCellReaderFactories(List<HSSFCellReaderFactory> cellReaderFactories) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(cellReaderFactories));

        this.cellReaderFactories.addAll(cellReaderFactories);
        AnnotationAwareOrderComparator.sort(this.cellReaderFactories);
    }

    public HSSFCellReader createFor(String path, Field field) {
        return cellReaderFactories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.createFor(path, field))
                .findFirst()
                .orElse(null);
    }
}
