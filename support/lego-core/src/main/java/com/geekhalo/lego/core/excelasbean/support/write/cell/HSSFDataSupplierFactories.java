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
public class HSSFDataSupplierFactories {
    private final List<HSSFHeaderDataSupplierFactory> headerDataSupplierFactories = Lists.newArrayList();
    private final List<HSSFDataDataSupplierFactory> dataDataSupplierFactories = Lists.newArrayList();

    public HSSFDataSupplierFactories(List<HSSFHeaderDataSupplierFactory> headerDataSupplierFactories,
                                     List<HSSFDataDataSupplierFactory> dataDataSupplierFactories) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(headerDataSupplierFactories));
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataDataSupplierFactories));

        this.headerDataSupplierFactories.addAll(headerDataSupplierFactories);
        this.dataDataSupplierFactories.addAll(dataDataSupplierFactories);

        AnnotationAwareOrderComparator.sort(this.headerDataSupplierFactories);
        AnnotationAwareOrderComparator.sort(this.dataDataSupplierFactories);
    }

    public HSSFDataSupplier createForHeader(AnnotatedElement element, String name) {
        return this.headerDataSupplierFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element, name))
                .findFirst()
                .orElse(null);
    }

    public HSSFDataSupplier createForData(AnnotatedElement element, String name) {
        return this.dataDataSupplierFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element, name))
                .findFirst()
                .orElse(null);
    }
}
