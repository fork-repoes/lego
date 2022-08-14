package com.geekhalo.lego.core.excelasbean.support.reader.cell;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFCellReader {
    /**
     * 从 Cell 中读取数据
     * @param cell
     * @return
     */
    Object readValue(HSSFCell cell);
}
