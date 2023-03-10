package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 向 Cell 写入数据
 */
public interface HSSFCellWriter<D> {
    /**
     * 向 Cell 写入数据
     * @param context
     * @param cell 待写入的 Cell
     * @param data 待写入的 data
     */
    void write(HSSFCellWriterContext context, HSSFCell cell, D data);
}
