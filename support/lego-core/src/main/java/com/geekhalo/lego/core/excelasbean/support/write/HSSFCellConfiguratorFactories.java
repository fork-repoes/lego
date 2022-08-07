package com.geekhalo.lego.core.excelasbean.support.write;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellConfiguratorFactories {
    private final List<HSSFCellConfiguratorFactory> factories;

    public HSSFCellConfiguratorFactories(List<HSSFCellConfiguratorFactory> factories) {
        this.factories = factories;
    }

    public List<HSSFCellConfigurator> create(Field field){
        return factories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.create(field))
                .collect(toList());
    }
}
