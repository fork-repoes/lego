package com.geekhalo.lego.core.excelasbean.support.write.header;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFHeaderWriterFactories {
    private final List<HSSFHeaderWriterFactory> factories;

    public HSSFHeaderWriterFactories(List<HSSFHeaderWriterFactory> factories) {
        this.factories = factories;
    }

    public HSSFHeaderWriter create(Field field){
        List<HSSFHeaderWriter> headerWriters = this.factories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.create(field))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(headerWriters)){
            return new DefaultHSSFHeaderWriter(field.getName());
        }

        if (headerWriters.size() > 1){
            throw new IllegalArgumentException(field.getName() + " Find " + headerWriters.size() +" Header Writer");
        }
        return headerWriters.get(0);
    }
}
