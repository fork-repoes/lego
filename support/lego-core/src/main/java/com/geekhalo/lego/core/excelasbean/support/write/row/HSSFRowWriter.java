package com.geekhalo.lego.core.excelasbean.support.write.row;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Row 写入器，写入一行数据
 */
public interface HSSFRowWriter<D> {
    /**
     * 写入 Header 行
     * @param context
     */
    void writeHeaderRow(HSSFRowWriterContext context);

    /**
     * 写入 数据 行
     * @param context
     * @param data
     */
     void writeDataRow(HSSFRowWriterContext context, D data);
}
