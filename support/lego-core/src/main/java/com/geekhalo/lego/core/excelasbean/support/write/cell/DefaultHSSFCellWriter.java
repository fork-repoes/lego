package com.geekhalo.lego.core.excelasbean.support.write.cell;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.Date;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFCellWriter implements HSSFCellWriter{

    @Override
    public void write(HSSFCellWriterContext context, HSSFCell cell, Object value) {
        Class returnType = value.getClass();
        if (returnType == String.class){
            cell.setCellValue((String) value);
        }
        if (returnType == Integer.class){
            cell.setCellValue(String.valueOf(value));
        }
        if (returnType == Long.class){
            cell.setCellValue(String.valueOf(value));
        }
        if (returnType == Date.class){
            cell.setCellValue((Date) value);
        }
    }
}
