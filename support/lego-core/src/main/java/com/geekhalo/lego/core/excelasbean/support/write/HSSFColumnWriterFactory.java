package com.geekhalo.lego.core.excelasbean.support.write;

import com.geekhalo.lego.core.excelasbean.support.write.data.*;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterChain;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFColumnWriterFactory {
    private final HSSFColumnOrderProviders orderProviders;
    private final HSSFHeaderWriterChainFactory headerWriterChainFactory;
    private final HSSFDataWriterChainFactory dataWriterChainFactory;

    public HSSFColumnWriterFactory(HSSFColumnOrderProviders orderProviders,
                                   HSSFHeaderWriterChainFactory headerWriterChainFactory,
                                   HSSFDataWriterChainFactory dataWriterChainFactory) {
        this.orderProviders = orderProviders;
        this.headerWriterChainFactory = headerWriterChainFactory;
        this.dataWriterChainFactory = dataWriterChainFactory;
    }


    public HSSFColumnWriter create(Field field){
        HSSFHeaderWriterChain hssfHeaderWriterChain = this.headerWriterChainFactory.create(field);

        HSSFDataWriterChain dataWriterChain = this.dataWriterChainFactory.create(field);

        int order = this.orderProviders.orderFor(field);

        return new DefaultHSSFColumnWriter<>(order, hssfHeaderWriterChain, dataWriterChain);
    }

}
