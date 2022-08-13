package com.geekhalo.lego.core.excelasbean.support.write.cell;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFCellStyleFactory {
    HSSFCellStyle createStyle(HSSFCellWriterContext context, String name);
}
