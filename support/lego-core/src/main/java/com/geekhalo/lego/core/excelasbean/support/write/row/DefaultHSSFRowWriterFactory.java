package com.geekhalo.lego.core.excelasbean.support.write.row;

import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriter;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFRowWriterFactory implements HSSFRowWriterFactory{
    private final HSSFColumnWriterFactories columnWriterFactories;

    public DefaultHSSFRowWriterFactory(HSSFColumnWriterFactories columnWriterFactories) {
        this.columnWriterFactories = columnWriterFactories;
    }

    @Override
    public <D> HSSFRowWriter<D> create(Class<D> cls) {
        List<HSSFColumnWriter> writers = this.columnWriterFactories.createForCls(cls);
        return new DefaultHSSFRowWriter(writers);
    }

}
