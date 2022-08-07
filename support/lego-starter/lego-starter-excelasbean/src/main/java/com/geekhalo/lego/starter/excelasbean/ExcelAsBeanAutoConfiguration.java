package com.geekhalo.lego.starter.excelasbean;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.DefaultExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.write.*;
import com.geekhalo.lego.core.excelasbean.support.write.data.*;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnClass(HSSFWorkbook.class)
public class ExcelAsBeanAutoConfiguration {

    @Bean
    public ExcelAsBeanService excelAsBeanService(HSSFColumnWritersFactory columnWritersFactory){
        return new DefaultExcelAsBeanService(columnWritersFactory);
    }

    @Bean
    public HSSFColumnWritersFactory hssfColumnWritersFactory(HSSFColumnWriterFactory factory){
        return new DefaultHSSFColumnWritersFactory(factory);
    }

    @Bean
    public HSSFColumnWriterFactory columnWriterFactory(HSSFColumnOrderProviders orderProviders,
                                                       HSSFHeaderWriterChainFactory headerWriterChainFactory,
                                                       HSSFDataWriterChainFactory dataWriterChainFactory){

        return new HSSFColumnWriterFactory(orderProviders, headerWriterChainFactory, dataWriterChainFactory);
    }

    @Bean
    public HSSFHeaderWriterChainFactory headerWriterChainFactory(HSSFCellConfiguratorFactories headerCellConfiguratorFactories,
                                                                 HSSFHeaderWriterFactories headerWriterFactories){
        return new HSSFHeaderWriterChainFactory(headerCellConfiguratorFactories, headerWriterFactories);
    }

    @Bean
    public HSSFDataWriterChainFactory dataWriterChainFactory(HSSFCellConfiguratorFactories dataCellConfiguratorFactories,
                                                             HSSFDataConverterFactories dataConverterFactories,
                                                             HSSFDataWriterFactories dataWriterFactories){
        return new HSSFDataWriterChainFactory(dataCellConfiguratorFactories, dataConverterFactories, dataWriterFactories);
    }

    @Bean
    public HSSFColumnOrderProviders columnOrderProviders(List<HSSFColumnOrderProvider> orderProviders){
        return new HSSFColumnOrderProviders(orderProviders);
    }

    @Bean
    public HSSFCellConfiguratorFactories cellConfiguratorFactories(List<HSSFCellConfiguratorFactory> factories){
        return new HSSFCellConfiguratorFactories(factories);
    }

    @Bean
    public HSSFHeaderWriterFactories headerWriterFactories(List<HSSFHeaderWriterFactory> factories){
        return new HSSFHeaderWriterFactories(factories);
    }

    @Bean
    public HSSFCellConfiguratorFactories dataCellConfiguratorFactories(List<HSSFCellConfiguratorFactory> factories){
        return new HSSFCellConfiguratorFactories(factories);
    }

    @Bean
    public HSSFDataConverterFactories dataConverterFactories(List<HSSFDataConverterFactory> factories){
        return new HSSFDataConverterFactories(factories);
    }

    @Bean
    public HSSFDataWriterFactories dataWriterFactories(List<HSSFDataWriterFactory> factories){
        return new HSSFDataWriterFactories(factories);
    }
}
