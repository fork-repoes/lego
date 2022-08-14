package com.geekhalo.lego.core.excelasbean.support.reader;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFSheetReader<D> {
    void writeTemplate(HSSFWorkbook workbook, HSSFSheet sheet);

    void readFromSheet(HSSFWorkbook workbook, HSSFSheet sheet, Consumer<D> consumer);
}
