package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import com.geekhalo.lego.core.excelasbean.support.reader.cell.HSSFCellReader;
import com.geekhalo.lego.core.excelasbean.support.reader.cell.HSSFCellReaderFactories;
import com.google.common.base.Preconditions;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultBeanPropertyWriterChainFactory implements BeanPropertyWriterChainFactory{
    private final HSSFCellReaderFactories cellReaderFactories;
    private final BeanPropertyWriterFactories beanPropertyWriterFactories;

    public DefaultBeanPropertyWriterChainFactory(HSSFCellReaderFactories cellReaderFactories,
                                                 BeanPropertyWriterFactories beanPropertyWriterFactories) {
        Preconditions.checkArgument(cellReaderFactories != null);
        Preconditions.checkArgument(beanPropertyWriterFactories != null);

        this.cellReaderFactories = cellReaderFactories;
        this.beanPropertyWriterFactories = beanPropertyWriterFactories;
    }

    @Override
    public BeanPropertyWriterChain createForField(String path, Field field) {
        HSSFCellReader cellReader = this.cellReaderFactories.createFor(path, field);
        BeanPropertyWriter beanPropertyWriter = this.beanPropertyWriterFactories.createFor(path, field);
        return new BeanPropertyWriterChain(cellReader, beanPropertyWriter);
    }
}
