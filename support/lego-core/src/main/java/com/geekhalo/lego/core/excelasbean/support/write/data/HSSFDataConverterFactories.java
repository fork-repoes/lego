package com.geekhalo.lego.core.excelasbean.support.write.data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFDataConverterFactories {
    private final List<HSSFDataConverterFactory> factories;

    public HSSFDataConverterFactories(List<HSSFDataConverterFactory> factories) {
        this.factories = factories;
    }

    public List<HSSFDataConverter> create(Field field){
        return this.factories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.create(field))
                .collect(Collectors.toList());
    }
}
