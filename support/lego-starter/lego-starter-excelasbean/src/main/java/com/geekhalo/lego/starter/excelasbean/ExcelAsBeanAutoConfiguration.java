package com.geekhalo.lego.starter.excelasbean;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.DefaultExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.write.cell.*;
import com.geekhalo.lego.core.excelasbean.support.write.column.DefaultHSSFColumnWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import com.geekhalo.lego.core.excelasbean.support.write.spi.*;
import com.geekhalo.lego.core.excelasbean.support.write.row.DefaultHSSFRowWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.DefaultHSSFSheetWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriterFactory;
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
    public ExcelAsBeanService excelAsBeanService(HSSFSheetWriterFactory columnWritersFactory){
        return new DefaultExcelAsBeanService(columnWritersFactory);
    }

    @Bean
    public HSSFSheetWriterFactory sheetWriterFactory(HSSFRowWriterFactory rowWriterFactory){
        return new DefaultHSSFSheetWriterFactory(rowWriterFactory);
    }

    @Bean
    public HSSFRowWriterFactory rowWriterFactory(HSSFColumnWriterFactories columnWriterFactories){
        return new DefaultHSSFRowWriterFactory(columnWriterFactories);
    }

    @Bean
    public HSSFColumnWriterFactories columnWriterFactories(HSSFColumnOrderProviders orderProviders,
                                                           HSSFColumnWriterFactory writerFactory){
        return new HSSFColumnWriterFactories(orderProviders, writerFactory);
    }

    @Bean
    public HSSFColumnWriterFactory columnWriterFactory(HSSFColumnOrderProviders orderProviders, HSSFCellWriterChainFactory writerChainFactory){
        return new DefaultHSSFColumnWriterFactory(orderProviders, writerChainFactory);
    }

    @Bean
    public HSSFCellWriterChainFactory writerChainFactory(HSSFDataSupplierFactories dataSupplierFactories,
                                                         HSSFCellConfiguratorFactories cellConfiguratorFactories,
                                                         HSSFCellWriterFactories cellWriterFactories){
        return new DefaultHSSFCellWriterChainFactory(dataSupplierFactories,
                cellConfiguratorFactories,
                cellWriterFactories);
    }

    @Bean
    public HSSFDataSupplierFactories dataSupplierFactories(List<HSSFHeaderDataSupplierFactory> headerDataSupplierFactories,
                                                           List<HSSFDataDataSupplierFactory> dataDataSupplierFactories){
        return new HSSFDataSupplierFactories(headerDataSupplierFactories, dataDataSupplierFactories);
    }

    @Bean
    public HSSFCellConfiguratorFactories cellConfiguratorFactories(List<HSSFHeaderCellConfiguratorFactory> headerCellFactories,
                                                                   List<HSSFDataCellConfiguratorFactory> dataCellFactories){
        return new HSSFCellConfiguratorFactories(headerCellFactories, dataCellFactories);
    }

    @Bean
    public HSSFCellWriterFactories cellWriterFactories(List<HSSFHeaderCellWriterFactory> headerCellWriterFactories,
                                                       List<HSSFDataCellWriterFactory> dataHeaderCellWriterFactories){
        return new HSSFCellWriterFactories(headerCellWriterFactories, dataHeaderCellWriterFactories);
    }

    @Bean
    public DefaultHSSFCellWriterFactory defaultHSSFCellWriterFactory(){
        return new DefaultHSSFCellWriterFactory();
    }

    @Bean
    public HSSFDateFormatCellWriterFactory dateFormatCellWriterFactory(){
        return new HSSFDateFormatCellWriterFactory();
    }

    @Bean
    public FieldBasedDataDataSupplierFactory fieldBasedDataDataSupplierFactory(){
        return new FieldBasedDataDataSupplierFactory();
    }

    @Bean
    public HSSFHeaderBasedHeaderDataSupplierFactory headerBasedHeaderDataSupplierFactory(){
        return new HSSFHeaderBasedHeaderDataSupplierFactory();
    }

    @Bean
    public HSSFColumnOrderProviders columnOrderProviders(List<HSSFColumnOrderProvider> orderProviders){
        return new HSSFColumnOrderProviders(orderProviders);
    }

    @Bean
    public HSSFIndexBasedHSSFColumnOrderProvider hssfIndexBasedHSSFColumnOrderProvider(){
        return new HSSFIndexBasedHSSFColumnOrderProvider();
    }

    @Bean
    public MethodBasedDataDataSupplierFactory methodBasedDataDataSupplierFactory(){
        return new MethodBasedDataDataSupplierFactory();
    }

    @Bean
    public AutoSizeCellConfiguratorFactory autoSizeHeaderCellConfiguratorFactory(){
        return new AutoSizeCellConfiguratorFactory();
    }
}
