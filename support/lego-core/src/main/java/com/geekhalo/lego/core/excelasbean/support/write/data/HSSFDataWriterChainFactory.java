package com.geekhalo.lego.core.excelasbean.support.write.data;

import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfiguratorFactories;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFDataWriterChainFactory {
    private final HSSFCellConfiguratorFactories dataCellConfiguratorFactories;
    private final HSSFDataConverterFactories dataConverterFactories;
    private final HSSFDataWriterFactories dataWriterFactories;

    public HSSFDataWriterChainFactory(HSSFCellConfiguratorFactories dataCellConfiguratorFactories,
                                      HSSFDataConverterFactories dataConverterFactories,
                                      HSSFDataWriterFactories dataWriterFactories) {
        this.dataCellConfiguratorFactories = dataCellConfiguratorFactories;
        this.dataConverterFactories = dataConverterFactories;
        this.dataWriterFactories = dataWriterFactories;
    }

    public HSSFDataWriterChain create(Field field) {
        List<HSSFCellConfigurator> dataCellConfigurators = this.dataCellConfiguratorFactories.create(field);
        List<HSSFDataConverter> dataConverters = this.dataConverterFactories.create(field);
        HSSFDataWriter dataWriter = this.dataWriterFactories.create(field);
        return new HSSFDataWriterChain(dataCellConfigurators, dataConverters, dataWriter);
    }
}
