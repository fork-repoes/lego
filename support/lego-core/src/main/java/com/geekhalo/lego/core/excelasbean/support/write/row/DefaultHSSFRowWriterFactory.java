package com.geekhalo.lego.core.excelasbean.support.write.row;

import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriter;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactories;

import java.util.List;

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
