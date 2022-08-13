package com.geekhalo.lego.core.excelasbean.support.write.cell;

import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.supplier.HSSFValueSupplier;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriter;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 对写入流程的封装，核心流程包括： <br />
 * 1.获取待写入数据 <br />
 * 2.配置 Cell <br />
 * 3.写入 数据 <br />
 */
public class HSSFCellWriterChain<D> {
    // 数据提供器，负责获取待写入数据
    private final HSSFValueSupplier dataSupplier;
    // Cell 配置器，对 Cell 进行配置
    private final List<HSSFCellConfigurator> cellConfigs = Lists.newArrayList();
    // Cell 写入器，负责数据写入
    private final HSSFCellWriter cellWriter;

    protected HSSFCellWriterChain(HSSFValueSupplier dataSupplier,
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
        // 创建 Cell
        HSSFRow row = context.getRow();
        int columnIndex = Math.max(0, row.getLastCellNum());
        HSSFCell cell = row.createCell(columnIndex);

        // 获取待写入数据
        Object d = this.dataSupplier.apply(data);

        // 配置 Cell
        configForCell(context, columnIndex, cell);

        // 写入数据
        this.cellWriter.write(context, cell, d);
    }

    protected void configForCell(HSSFCellWriterContext context, int columnIndex, HSSFCell cell) {
        this.cellConfigs.forEach(cellConfigurator -> cellConfigurator.configFor(context, columnIndex, cell));
    }

    public void addCellConfigurator(HSSFCellConfigurator cellConfigurator){
        this.cellConfigs.add(cellConfigurator);
        AnnotationAwareOrderComparator.sort(this.cellConfigs);
    }
}
