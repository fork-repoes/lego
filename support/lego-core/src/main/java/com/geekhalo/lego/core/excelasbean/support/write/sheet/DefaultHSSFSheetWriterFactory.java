package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriter;
import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriterFactory;
import com.google.common.base.Preconditions;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFSheetWriterFactory implements HSSFSheetWriterFactory{
    private final HSSFRowWriterFactory rowWriterFactory;

    public DefaultHSSFSheetWriterFactory(HSSFRowWriterFactory rowWriterFactory) {
        Preconditions.checkArgument(rowWriterFactory != null);
        this.rowWriterFactory = rowWriterFactory;
    }


    @Override
    public <D> HSSFSheetWriter<D> createFor(Class<D> cls) {
        HSSFRowWriter hssfRowWriter = this.rowWriterFactory.create((Class) cls);
        return new DefaultHSSFSheetWriter(hssfRowWriter);
    }
}
