package com.geekhalo.lego.starter.excelasbean;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.DefaultExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.DefaultBeanPropertyWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.column.DefaultHSSFColumnToBeanPropertyWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.column.HSSFColumnToBeanPropertyWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.reader.column.HSSFColumnToBeanPropertyWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.DefaultHSSFHeaderParser;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.geekhalo.lego.core.excelasbean.support.reader.row.DefaultHSSFRowToBeanWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.row.HSSFRowToBeanWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.sheet.DefaultHSSFSheetReaderFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.sheet.HSSFSheetReaderFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.*;
import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.*;
import com.geekhalo.lego.core.excelasbean.support.write.cell.style.HSSFCellStyleFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.style.HSSFCellStyleFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.supplier.*;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.*;
import com.geekhalo.lego.core.excelasbean.support.write.column.DefaultHSSFColumnWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactories;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriterFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFHeaderBasedColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFShowOrderBasedColumnOrderProvider;
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
    public ExcelAsBeanService excelAsBeanService(HSSFSheetWriterFactory columnWritersFactory,
                                                 HSSFSheetReaderFactory readerFactory){
        return new DefaultExcelAsBeanService(columnWritersFactory, readerFactory);
    }

    @Bean
    public HSSFSheetReaderFactory readerFactory(HSSFRowToBeanWriterFactory rowToBeanWriterFactory){
        return new DefaultHSSFSheetReaderFactory(rowToBeanWriterFactory);
    }

    @Bean
    public HSSFRowToBeanWriterFactory rowToBeanWriterFactory(HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories,
                                                             HSSFHeaderParser headerParser){
        return new DefaultHSSFRowToBeanWriterFactory(columnToBeanWriterFactories, headerParser);
    }

    @Bean
    public HSSFColumnToBeanPropertyWriterFactories columnToBeanWriterFactories(List<HSSFColumnToBeanPropertyWriterFactory> columnToBeanWriterFactories,
                                                                               HSSFHeaderParser headerParser){
        return new HSSFColumnToBeanPropertyWriterFactories(columnToBeanWriterFactories, headerParser);
    }

    @Bean
    public DefaultHSSFColumnToBeanPropertyWriterFactory defaultHSSFColumnToBeanWriterFactory(BeanPropertyWriterChainFactory beanPropertyWriterChainFactory,
                                                                                             HSSFHeaderParser headerParser){
        return new DefaultHSSFColumnToBeanPropertyWriterFactory(beanPropertyWriterChainFactory, headerParser);
    }

    @Bean
    public BeanPropertyWriterChainFactory beanPropertyWriterChainFactory(){
        return new DefaultBeanPropertyWriterChainFactory();
    }

    @Bean
    public HSSFHeaderParser headerParser(){
        return new DefaultHSSFHeaderParser();
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
                                                           HSSFColumnWriterFactory writerFactory,
                                                           HSSFCellConfiguratorFactories cellConfiguratorFactories){
        return new HSSFColumnWriterFactories(orderProviders, writerFactory, cellConfiguratorFactories);
    }

    @Bean
    public HSSFColumnWriterFactory columnWriterFactory(HSSFColumnOrderProviders orderProviders, HSSFCellWriterChainFactory writerChainFactory){
        return new DefaultHSSFColumnWriterFactory(orderProviders, writerChainFactory);
    }

    @Bean
    public HSSFCellWriterChainFactory writerChainFactory(HSSFValueSupplierFactories dataSupplierFactories,
                                                         HSSFCellConfiguratorFactories cellConfiguratorFactories,
                                                         HSSFCellWriterFactories cellWriterFactories){
        return new DefaultHSSFCellWriterChainFactory(dataSupplierFactories,
                cellConfiguratorFactories,
                cellWriterFactories);
    }

    @Bean
    public HSSFValueSupplierFactories dataSupplierFactories(List<HSSFHeaderValueSupplierFactory> headerDataSupplierFactories,
                                                            List<HSSFDataValueSupplierFactory> dataDataSupplierFactories){
        return new HSSFValueSupplierFactories(headerDataSupplierFactories, dataDataSupplierFactories);
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
    public FieldBasedDataValueSupplierFactory fieldBasedDataDataSupplierFactory(){
        return new FieldBasedDataValueSupplierFactory();
    }

    @Bean
    public HSSFHeaderBasedHeaderValueSupplierFactory headerBasedHeaderDataSupplierFactory(){
        return new HSSFHeaderBasedHeaderValueSupplierFactory();
    }

    @Bean
    public HSSFColumnOrderProviders columnOrderProviders(List<HSSFColumnOrderProvider> orderProviders){
        return new HSSFColumnOrderProviders(orderProviders);
    }

    @Bean
    public HSSFHeaderBasedColumnOrderProvider hssfIndexBasedHSSFColumnOrderProvider(){
        return new HSSFHeaderBasedColumnOrderProvider();
    }

    @Bean
    public HSSFShowOrderBasedColumnOrderProvider showOrderBasedColumnOrderProvider(){
        return new HSSFShowOrderBasedColumnOrderProvider();
    }

    @Bean
    public MethodBasedDataValueSupplierFactory methodBasedDataDataSupplierFactory(){
        return new MethodBasedDataValueSupplierFactory();
    }

    @Bean
    public AutoSizeCellConfiguratorFactory autoSizeHeaderCellConfiguratorFactory(){
        return new AutoSizeCellConfiguratorFactory();
    }

    @Bean
    public HSSFDataCellStyleConfiguratorFactory hssfDataCellStyleConfiguratorFactory(HSSFCellStyleFactories cellStyleFactories){
        return new HSSFDataCellStyleConfiguratorFactory(cellStyleFactories);
    }

    @Bean
    public HSSFHeaderCellStyleConfiguratorFactory hssfHeaderCellStyleConfiguratorFactory(HSSFCellStyleFactories cellStyleFactories){
        return new HSSFHeaderCellStyleConfiguratorFactory(cellStyleFactories);
    }

    @Bean
    public HSSFCellStyleFactories cellStyleFactories(List<HSSFCellStyleFactory> cellStyleFactories){
        return new HSSFCellStyleFactories(cellStyleFactories);
    }
}
