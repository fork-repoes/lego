package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Cell Configurator 统一配置工厂
 */
public class HSSFCellConfiguratorFactories {
    private final List<HSSFHeaderCellConfiguratorFactory> headerCellFactories = Lists.newArrayList();
    private final List<HSSFDataCellConfiguratorFactory> dataCellFactories = Lists.newArrayList();

    public HSSFCellConfiguratorFactories(List<HSSFHeaderCellConfiguratorFactory> headerCellFactories,
                                         List<HSSFDataCellConfiguratorFactory> dataCellFactories) {
        if (CollectionUtils.isNotEmpty(headerCellFactories)) {
            this.headerCellFactories.addAll(headerCellFactories);
        }
        if (CollectionUtils.isNotEmpty(dataCellFactories)) {
            this.dataCellFactories.addAll(dataCellFactories);
        }

        AnnotationAwareOrderComparator.sort(this.headerCellFactories);
        AnnotationAwareOrderComparator.sort(this.dataCellFactories);
    }

    /**
     * 对 Header Cell 进行配置
     * @param element
     * @return
     */
    public List<HSSFCellConfigurator> createForHeader(AnnotatedElement element) {
        return this.headerCellFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element))
                .collect(toList());
    }

    /**
     * 对 Data Cell 进行配置
     * @param element
     * @return
     */
    public List<HSSFCellConfigurator> createForData(AnnotatedElement element) {
        return this.dataCellFactories.stream()
                .filter(factory -> factory.support(element))
                .map(factory -> factory.create(element))
                .collect(toList());
    }
}
