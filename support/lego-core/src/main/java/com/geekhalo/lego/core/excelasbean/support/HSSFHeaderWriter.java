package com.geekhalo.lego.core.excelasbean.support;

import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入头信息
 */
public interface HSSFHeaderWriter {
    /**
     * 写入表头
     * @param cell
     */
    void writeHeader(HSSFCell cell);
}
