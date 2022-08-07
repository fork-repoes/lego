package com.geekhalo.lego.core.excelasbean.support.write.header;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFHeaderWriter implements HSSFHeaderWriter{
    private final String title;

    public DefaultHSSFHeaderWriter(String title) {
        this.title = title;
    }

    @Override
    public void writeHeader(HSSFCell cell) {
        cell.setCellValue(this.title);
    }
}
