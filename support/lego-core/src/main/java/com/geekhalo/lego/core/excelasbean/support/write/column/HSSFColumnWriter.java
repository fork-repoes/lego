package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import org.springframework.core.Ordered;

/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入一列数据，包括数据头，和数据信息
 */
public interface HSSFColumnWriter<D> extends Ordered {

    void writeDataCell(HSSFCellWriterContext context, D data);

    void writeHeaderCell(HSSFCellWriterContext context);
}
