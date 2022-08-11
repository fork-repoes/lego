package com.geekhalo.lego.core.excelasbean.support.write.row;

import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterContext;
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
 */
public class DefaultHSSFRowWriter<D> implements HSSFRowWriter<D> {
    private final List<HSSFRowConfigurator> headerRowConfigurators = Lists.newArrayList();
    private final List<HSSFRowConfigurator> dataRowConfigurators = Lists.newArrayList();

    private final List<HSSFColumnWriter> columnWriters = Lists.newArrayList();

    public DefaultHSSFRowWriter(List<HSSFColumnWriter> columnWriters){
        this(null, null, columnWriters);
    }

    public DefaultHSSFRowWriter(List<HSSFRowConfigurator> headerRowConfigurators,
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

        AnnotationAwareOrderComparator.sort(this.columnWriters);
    }

    @Override
    public void writeHead(HSSFRowWriterContext context) {
        HSSFRow row = context.getSheet().createRow(0);
        configForHeader(context, row);

        for (int i = 0; i < columnWriters.size(); i++) {
            HSSFCellWriterContext cellWriterContext = HSSFCellWriterContext.builder()
                    .workbook(context.getWorkbook())
                    .sheet(context.getSheet())
                    .row(row)
                    .build();

            this.columnWriters.get(i)
                    .writeHeader(cellWriterContext);
        }
    }

    private void configForHeader(HSSFRowWriterContext context, HSSFRow row) {
        this.headerRowConfigurators.forEach(configurator -> configurator.configFor(context, row));
    }

    @Override
    public void writeData(HSSFRowWriterContext context, D data) {
        HSSFRow row = context.getSheet().createRow(context.getSheet().getLastRowNum() + 1);

        configForData(context, row);

        for (int i = 0; i < columnWriters.size(); i++) {
            HSSFCellWriterContext cellWriterContext = HSSFCellWriterContext.builder()
                    .workbook(context.getWorkbook())
                    .sheet(context.getSheet())
                    .row(row)
                    .build();


            this.columnWriters.get(i)
                    .writeData(cellWriterContext, data);
        }
    }

    private void configForData(HSSFRowWriterContext context, HSSFRow row) {
        this.dataRowConfigurators.forEach(configurator -> configurator.configFor(context, row));
    }
}
