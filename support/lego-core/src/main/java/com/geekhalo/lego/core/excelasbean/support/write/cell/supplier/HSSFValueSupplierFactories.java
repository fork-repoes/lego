package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

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
 *
 * ValueSupplier 总工厂，提供统一的 创建能力
 */
public class HSSFValueSupplierFactories {
    private final List<HSSFHeaderValueSupplierFactory> headerValueSupplierFactories = Lists.newArrayList();
    private final List<HSSFDataValueSupplierFactory> dataValueSupplierFactories = Lists.newArrayList();

    public HSSFValueSupplierFactories(List<HSSFHeaderValueSupplierFactory> headerValueSupplierFactories,
                                      List<HSSFDataValueSupplierFactory> dataValueSupplierFactories) {

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(headerValueSupplierFactories));
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataValueSupplierFactories));

        this.headerValueSupplierFactories.addAll(headerValueSupplierFactories);
        this.dataValueSupplierFactories.addAll(dataValueSupplierFactories);

        AnnotationAwareOrderComparator.sort(this.headerValueSupplierFactories);
        AnnotationAwareOrderComparator.sort(this.dataValueSupplierFactories);
    }

    public HSSFValueSupplier createForHeader(AnnotatedElement element) {
        return this.headerValueSupplierFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element))
                .findFirst()
                .orElse(null);
    }

    public HSSFValueSupplier createForData(AnnotatedElement element) {
        return this.dataValueSupplierFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element))
                .findFirst()
                .orElse(null);
    }
}
