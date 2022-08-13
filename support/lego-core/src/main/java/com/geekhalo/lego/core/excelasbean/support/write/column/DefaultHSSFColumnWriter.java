package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterChain;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * HSSFColumnWriter 默认实现，负责 Cell 写入，包括： <br />
 * 1. 写入 Header Cell <br />
 * 2. 写入 Data Cell <br />
 */
public class DefaultHSSFColumnWriter<D> implements HSSFColumnWriter<D>{
    // 写入顺序
    private final int order;
    // Header 写入链
    private final HSSFCellWriterChain headerWriterChain;
    // Data 写入链
    private final HSSFCellWriterChain dataWriterChain;

    public DefaultHSSFColumnWriter(int order,
                                   HSSFCellWriterChain headerWriterChain,
                                   HSSFCellWriterChain dataWriterChain) {
        Preconditions.checkArgument(headerWriterChain != null);
        Preconditions.checkArgument(dataWriterChain != null);

        this.order = order;
        this.headerWriterChain = headerWriterChain;
        this.dataWriterChain = dataWriterChain;
    }

    @Override
    public int getOrder() {
        return this.order;
    }


    @Override
    public void writeDataCell(HSSFCellWriterContext context, D data) {
        this.dataWriterChain.write(context, data);
    }

    @Override
    public void writeHeaderCell(HSSFCellWriterContext context) {
        this.headerWriterChain.write(context, null);
    }

    private void addHeaderCellConfigurator(HSSFCellConfigurator cellConfigurator){
        this.headerWriterChain.addCellConfigurator(cellConfigurator);
    }

    private void addDataCellConfigurator(HSSFCellConfigurator cellConfigurator){
        this.dataWriterChain.addCellConfigurator(cellConfigurator);
    }

    /**
     * 添加 DataCellConfigurator
     * @param dataCellConfigurators
     */
    public void addDataCellConfigurators(List<HSSFCellConfigurator> dataCellConfigurators) {
        if (CollectionUtils.isNotEmpty(dataCellConfigurators)){
            dataCellConfigurators.forEach(this::addDataCellConfigurator);
        }
    }

    /**
     * 添加 headerCellConfigurators
     * @param headerCellConfigurators
     */
    public void addHeaderCellConfigurators(List<HSSFCellConfigurator> headerCellConfigurators) {
        if (CollectionUtils.isNotEmpty(headerCellConfigurators)){
            headerCellConfigurators.forEach(this::addHeaderCellConfigurator);
        }
    }
}
