package com.geekhalo.lego.core.excelasbean.support.reader.row;

import com.geekhalo.lego.core.excelasbean.support.reader.column.HSSFColumnToBeanPropertyWriter;
import com.geekhalo.lego.core.excelasbean.support.reader.column.HSSFColumnToBeanPropertyWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class DefaultHSSFRowToBeanWriterFactory implements HSSFRowToBeanWriterFactory {
    private final HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories;
    private final HSSFHeaderParser headerParser;

    public DefaultHSSFRowToBeanWriterFactory(HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories,
                                             HSSFHeaderParser headerParser) {
        Preconditions.checkArgument(columnToBeanWriterFactories != null);
        Preconditions.checkArgument(headerParser != null);

        this.columnToBeanWriterFactories = columnToBeanWriterFactories;
        this.headerParser = headerParser;
    }

    @Override
    public <D> HSSFRowToBeanWriter<D> createForType(Class<D> cls) {
        List<HSSFColumnToBeanPropertyWriter> columnToBeanWriters = this.columnToBeanWriterFactories.create(null, cls);

        return new HSSFRowToBeanWriter(new ConstructorBasedSupplier(cls) , headerParser, columnToBeanWriters);
    }

    class ConstructorBasedSupplier implements Supplier{
        private final Class cls;

        ConstructorBasedSupplier(Class cls) {
            this.cls = cls;
        }

        @Override
        public Object get() {
            try {
                return ConstructorUtils.invokeConstructor(cls);
            } catch (Exception e) {
                log.error("failed to create bean {}", cls, e);
            }
            return null;
        }
    }


}
