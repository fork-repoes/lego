package com.geekhalo.lego.core.excelasbean.support.write;

import com.geekhalo.lego.core.excelasbean.support.write.data.HSSFDataWriterChain;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterChain;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFColumnWriter<D> implements HSSFColumnWriter<D>{
    private final int order;
    private final HSSFHeaderWriterChain headerWriterChain;
    private final HSSFDataWriterChain dataWriterChain;

    public DefaultHSSFColumnWriter(int order,
                                   HSSFHeaderWriterChain headerWriterChain,
                                   HSSFDataWriterChain dataWriterChain) {
        this.order = order;
        this.headerWriterChain = headerWriterChain;
        this.dataWriterChain = dataWriterChain;
    }

    @Override
    public void writeData(HSSFCell cell, D data) {
        this.dataWriterChain.write(cell, data);
    }

    @Override
    public void writeHeader(HSSFCell cell) {
        this.headerWriterChain.write(cell);
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
