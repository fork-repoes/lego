package com.geekhalo.lego.core.excelasbean.support.write.row;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.geekhalo.lego.core.excelasbean.support.write.column.HSSFColumnWriter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入一行数据
 */
public class DefaultHSSFRowWriter<D> implements HSSFRowWriter<D> {
    // Header 配置器，预留扩展点
    private final List<HSSFRowConfigurator> headerRowConfigurators = Lists.newArrayList();
    // Data 配置器，预留扩展点
    private final List<HSSFRowConfigurator> dataRowConfigurators = Lists.newArrayList();

    // 列写入器
    private final List<HSSFColumnWriter> columnWriters = Lists.newArrayList();

    public DefaultHSSFRowWriter(List<HSSFColumnWriter> columnWriters){
        this(null, null, columnWriters);
    }

    private DefaultHSSFRowWriter(List<HSSFRowConfigurator> headerRowConfigurators,
                                List<HSSFRowConfigurator> dataRowConfigurators,
                                List<HSSFColumnWriter> columnWriters) {

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(columnWriters));

        if (CollectionUtils.isNotEmpty(headerRowConfigurators)) {
            this.headerRowConfigurators.addAll(headerRowConfigurators);
        }
        if (CollectionUtils.isNotEmpty(dataRowConfigurators)) {
            this.dataRowConfigurators.addAll(dataRowConfigurators);
        }

        this.columnWriters.addAll(columnWriters);

        // 对列写入器进行排序
        AnnotationAwareOrderComparator.sort(this.columnWriters);
    }

    @Override
    public void writeHeaderRow(HSSFRowWriterContext context) {
        // 第一行默认为 Header
        HSSFRow row = context.getSheet()
                .createRow(0);

        // 对 Row 进行配置
        configForHeader(context, row);

        // 为每一列写入 Header
        for (int i = 0; i < columnWriters.size(); i++) {
            HSSFCellWriterContext cellWriterContext = HSSFCellWriterContext.builder()
                    .workbook(context.getWorkbook())
                    .sheet(context.getSheet())
                    .row(row)
                    .build();

            // 写入 Header 信息
            this.columnWriters.get(i)
                    .writeHeaderCell(cellWriterContext);
        }
    }

    private void configForHeader(HSSFRowWriterContext context, HSSFRow row) {
        this.headerRowConfigurators.forEach(configurator -> configurator.configFor(context, row));
    }

    @Override
    public void writeDataRow(HSSFRowWriterContext context, D data) {
        // 创建数据行
        HSSFRow row = context.getSheet()
                .createRow(context.getSheet().getLastRowNum() + 1);

        // 配置 Data 行
        configForData(context, row);

        // 为每一列写入数据
        for (int i = 0; i < columnWriters.size(); i++) {
            HSSFCellWriterContext cellWriterContext = HSSFCellWriterContext.builder()
                    .workbook(context.getWorkbook())
                    .sheet(context.getSheet())
                    .row(row)
                    .build();


            this.columnWriters.get(i)
                    .writeDataCell(cellWriterContext, data);
        }
    }

    private void configForData(HSSFRowWriterContext context, HSSFRow row) {
        this.dataRowConfigurators.forEach(configurator -> configurator.configFor(context, row));
    }
}
