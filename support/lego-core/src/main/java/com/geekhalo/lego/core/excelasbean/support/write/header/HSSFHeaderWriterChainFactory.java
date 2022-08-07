package com.geekhalo.lego.core.excelasbean.support.write.header;

import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfiguratorFactories;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFHeaderWriterChainFactory {
    private final HSSFCellConfiguratorFactories headerCellConfiguratorFactories;
    private final HSSFHeaderWriterFactories headerWriterFactories;

    public HSSFHeaderWriterChainFactory(HSSFCellConfiguratorFactories headerCellConfiguratorFactories,
                                        HSSFHeaderWriterFactories headerWriterFactories) {
        this.headerCellConfiguratorFactories = headerCellConfiguratorFactories;
        this.headerWriterFactories = headerWriterFactories;
    }

    public HSSFHeaderWriterChain create(Field field) {
        List<HSSFCellConfigurator> headerCellConfigurators = this.headerCellConfiguratorFactories.create(field);
        HSSFHeaderWriter hssfHeaderWriter = this.headerWriterFactories.create(field);
        return new HSSFHeaderWriterChain(headerCellConfigurators, hssfHeaderWriter);
    }
}
