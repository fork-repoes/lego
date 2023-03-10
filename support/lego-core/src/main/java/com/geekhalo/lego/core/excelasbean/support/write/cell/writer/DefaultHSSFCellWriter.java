package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Date;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 默认写入器，处理常规类型的写入操作
 */
public class DefaultHSSFCellWriter implements HSSFCellWriter {

    @Override
    public void write(HSSFCellWriterContext context, HSSFCell cell, Object value) {
        if (value == null){
            return;
        }
        Class returnType = value.getClass();
        if (returnType == String.class){
            cell.setCellValue((String) value);
        }

        if (returnType == Integer.class
                || returnType == Integer.TYPE){
            cell.setCellValue(String.valueOf(value));
        }

        if (returnType == Long.class
                || returnType == Long.TYPE){
            cell.setCellValue(String.valueOf(value));
        }

        if (returnType == Double.class){
            cell.setCellValue(Double.valueOf(String.valueOf(value)));
        }

        if (returnType == Date.class){
            HSSFDataFormat df = context.getWorkbook().createDataFormat();
            CellStyle cs = context.getWorkbook().createCellStyle();
            cs.setDataFormat(df.getFormat("yyyy-mm-dd HH:MM"));
            cell.setCellStyle(cs);
            cell.setCellValue((Date) value);
        }
    }
}
