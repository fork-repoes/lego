package com.geekhalo.lego.core.excelasbean.support.write.data;

import com.geekhalo.lego.core.excelasbean.support.write.header.DefaultHSSFHeaderWriter;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriter;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFDataWriterFactories {
    private final List<HSSFDataWriterFactory> factories;

    public HSSFDataWriterFactories(List<HSSFDataWriterFactory> factories) {
        this.factories = factories;
    }

    public HSSFDataWriter create(Field field){
        List<HSSFDataWriter> dataWriters = this.factories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.create(field))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataWriters)){
            return new DefaultHSSFDataWriter(field.getName(), "-");
        }

        if (dataWriters.size() > 1){
            throw new IllegalArgumentException(field.getName() + " Find " + dataWriters.size() +" Data Writer");
        }
        return dataWriters.get(0);
    }
}
