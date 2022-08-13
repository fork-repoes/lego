package com.geekhalo.lego.core.excelasbean.support.write.cell;

import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFCellConfiguratorFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.supplier.HSSFValueSupplier;
import com.geekhalo.lego.core.excelasbean.support.write.cell.supplier.HSSFValueSupplierFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriter;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterFactories;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFCellWriterChainFactory implements HSSFCellWriterChainFactory{
    private final HSSFValueSupplierFactories dataSupplierFactories;
    private final HSSFCellConfiguratorFactories cellConfiguratorFactories;
    private final HSSFCellWriterFactories cellWriterFactories;

    public DefaultHSSFCellWriterChainFactory(HSSFValueSupplierFactories dataSupplierFactories,
                                             HSSFCellConfiguratorFactories cellConfiguratorFactories,
                                             HSSFCellWriterFactories cellWriterFactories) {
        this.dataSupplierFactories = dataSupplierFactories;
        this.cellConfiguratorFactories = cellConfiguratorFactories;
        this.cellWriterFactories = cellWriterFactories;
    }


    @Override
    public <D> HSSFCellWriterChain<D> createHeaderWriterChain(AnnotatedElement element) {
        HSSFValueSupplier dataSupplier = this.dataSupplierFactories.createForHeader(element);
        List<HSSFCellConfigurator> cellConfigs = this.cellConfiguratorFactories.createForHeader(element);
        HSSFCellWriter cellWriter = cellWriterFactories.createForHeader(element);

        if (dataSupplier == null || cellWriter == null){
            return null;
        }

        return new HSSFCellWriterChain<>(dataSupplier, cellConfigs, cellWriter);
    }

    @Override
    public <D> HSSFCellWriterChain<D> createDataWriterChain(AnnotatedElement element) {
        HSSFValueSupplier dataSupplier = this.dataSupplierFactories.createForData(element);
        List<HSSFCellConfigurator> cellConfigs = this.cellConfiguratorFactories.createForData(element);
        HSSFCellWriter cellWriter = this.cellWriterFactories.createForData(element);

        if (dataSupplier == null || cellWriter == null){
            return null;
        }

        return new HSSFCellWriterChain<>(dataSupplier, cellConfigs, cellWriter);
    }
}
