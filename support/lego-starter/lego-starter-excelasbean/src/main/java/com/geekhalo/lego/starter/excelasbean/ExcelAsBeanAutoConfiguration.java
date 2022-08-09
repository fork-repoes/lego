package com.geekhalo.lego.starter.excelasbean;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.DefaultExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfiguratorFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfiguratorFactory;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProviders;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFIndexBasedHSSFColumnOrderProvider;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriterFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HSSFColumnOrderProviders columnOrderProviders(List<HSSFColumnOrderProvider> orderProviders){
        return new HSSFColumnOrderProviders(orderProviders);
    }

    @Bean
    public HSSFCellConfiguratorFactories headerCellConfiguratorFactories(@Autowired(required = false)
                                                                               List<HSSFCellConfiguratorFactory> factories){
        return new HSSFCellConfiguratorFactories(factories);
    }



    @Bean
    public HSSFCellConfiguratorFactories dataCellConfiguratorFactories(@Autowired(required = false)
                                                                                   List<HSSFCellConfiguratorFactory> factories){
        return new HSSFCellConfiguratorFactories(factories);
    }

    @Bean
    public HSSFIndexBasedHSSFColumnOrderProvider hssfIndexBasedHSSFColumnOrderProvider(){
        return new HSSFIndexBasedHSSFColumnOrderProvider();
    }
}
