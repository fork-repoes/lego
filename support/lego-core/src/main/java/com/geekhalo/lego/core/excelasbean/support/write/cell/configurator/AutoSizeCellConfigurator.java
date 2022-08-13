package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class AutoSizeCellConfigurator implements HSSFCellConfigurator{
    private final boolean autoSizeColumn;

    public AutoSizeCellConfigurator(boolean autoSizeColumn) {
        this.autoSizeColumn = autoSizeColumn;
    }

    @Override
    public void configFor(HSSFCellWriterContext context, int columnIndex, HSSFCell cell) {
        if (autoSizeColumn) {
            context.getSheet().autoSizeColumn(columnIndex);
        }
    }
}
