package com.geekhalo.lego.core.excelasbean.support.write.cell;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellWriterChain<D> {
    private final Function dataSupplier;
    private final List<HSSFCellConfigurator> cellConfigs = Lists.newArrayList();
    private final HSSFCellWriter cellWriter;

    protected HSSFCellWriterChain(Function dataSupplier,
                                  List<HSSFCellConfigurator> cellConfigs,
                                  HSSFCellWriter cellWriter){
        Preconditions.checkArgument(dataSupplier != null);
        Preconditions.checkArgument(cellWriter != null);

        this.dataSupplier = dataSupplier;
        this.cellWriter = cellWriter;


        if (CollectionUtils.isNotEmpty(cellConfigs)) {
            this.cellConfigs.addAll(cellConfigs);
        }
        AnnotationAwareOrderComparator.sort(this.cellConfigs);
    }

    public void write(HSSFCellWriterContext context, D data){
        HSSFRow row = context.getRow();
        HSSFCell cell = row.createCell(row.getLastCellNum());
        configForCell(context, cell);

        Object d = this.dataSupplier.apply(data);
        this.cellWriter.write(context, cell, d);
    }

    protected void configForCell(HSSFCellWriterContext context, HSSFCell cell) {
        this.cellConfigs.forEach(hssfCellConfigurator -> hssfCellConfigurator.configFor(context, cell));
    }
}
