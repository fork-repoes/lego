package com.geekhalo.lego.core.excelasbean.support.reader.sheet;

import com.geekhalo.lego.core.excelasbean.support.reader.row.HSSFRowToBeanWriter;
import com.geekhalo.lego.core.excelasbean.support.reader.row.HSSFRowToBeanWriterFactory;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFSheetReaderFactory implements HSSFSheetReaderFactory {
    private final HSSFRowToBeanWriterFactory rowToBeanWriterFactory;

    public DefaultHSSFSheetReaderFactory(HSSFRowToBeanWriterFactory rowToBeanWriterFactory) {
        this.rowToBeanWriterFactory = rowToBeanWriterFactory;
    }

    @Override
    public <D> HSSFSheetReader<D> createFor(Class<D> cls) {
        HSSFRowToBeanWriter<D> rowToBeanWriter = rowToBeanWriterFactory.createForType(cls);
        return new DefaultHSSFSheetReader<>(cls, rowToBeanWriter);
    }
}
