package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 完成对 Cell 的配置
 */
public interface HSSFCellConfigurator {
    /**
     * 对 Cell 进行配置
     * @param context
     * @param columnIndex
     * @param cell
     */
    void configFor(HSSFCellWriterContext context, int columnIndex, HSSFCell cell);
}
