package com.geekhalo.lego.core.excelasbean.support.write.cell;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFCellWriterChainFactory implements HSSFCellWriterChainFactory{
    private final HSSFDataSupplierFactories dataSupplierFactories;
    private final HSSFCellConfiguratorFactories cellConfiguratorFactories;
    private final HSSFCellWriterFactories cellWriterFactories;

    public DefaultHSSFCellWriterChainFactory(HSSFDataSupplierFactories dataSupplierFactories,
                                             HSSFCellConfiguratorFactories cellConfiguratorFactories,
                                             HSSFCellWriterFactories cellWriterFactories) {
        this.dataSupplierFactories = dataSupplierFactories;
        this.cellConfiguratorFactories = cellConfiguratorFactories;
        this.cellWriterFactories = cellWriterFactories;
    }


    @Override
    public <D> HSSFCellWriterChain<D> createHeaderWriterChain(AnnotatedElement element, String name) {
        HSSFDataSupplier dataSupplier = this.dataSupplierFactories.createForHeader(element, name);
        List<HSSFCellConfigurator> cellConfigs = this.cellConfiguratorFactories.createForHeader(element, name);
        HSSFCellWriter cellWriter = cellWriterFactories.createForHeader(element, name);

        if (dataSupplier == null || cellWriter == null){
            return null;
        }

        return new HSSFCellWriterChain<>(dataSupplier, cellConfigs, cellWriter);
    }

    @Override
    public <D> HSSFCellWriterChain<D> createDataWriterChain(AnnotatedElement element, String name) {
        HSSFDataSupplier dataSupplier = this.dataSupplierFactories.createForData(element, name);
        List<HSSFCellConfigurator> cellConfigs = this.cellConfiguratorFactories.createForData(element, name);
        HSSFCellWriter cellWriter = this.cellWriterFactories.createForData(element, name);

        if (dataSupplier == null || cellWriter == null){
            return null;
        }

        return new HSSFCellWriterChain<>(dataSupplier, cellConfigs, cellWriter);
    }
}
