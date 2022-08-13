package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterChain;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterContext;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFColumnWriter<D> implements HSSFColumnWriter<D>{
    private final int order;
    private final HSSFCellWriterChain headerWriterChain;
    private final HSSFCellWriterChain dataWriterChain;

    public DefaultHSSFColumnWriter(int order,
                                   HSSFCellWriterChain headerWriterChain,
                                   HSSFCellWriterChain dataWriterChain) {
        this.order = order;
        this.headerWriterChain = headerWriterChain;
        this.dataWriterChain = dataWriterChain;
    }

    @Override
    public int getOrder() {
        return this.order;
    }


    @Override
    public void writeData(HSSFCellWriterContext context, D data) {
        this.dataWriterChain.write(context, data);
    }

    @Override
    public void writeHeader(HSSFCellWriterContext context) {
        this.headerWriterChain.write(context, null);
    }
}
