package com.geekhalo.lego.core.excelasbean.support.reader.sheet;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 从 Sheet 中读取数据，并将其转换为 JavaBean 对象
 */
public interface HSSFSheetReader<D> {

    /**
     * 将 模板 以 Header 的方式写入 Sheet
     * @param workbook
     * @param sheet
     */
    void writeTemplate(HSSFWorkbook workbook, HSSFSheet sheet);

    /**
     * 读取 Sheet 中的数据
     * @param workbook
     * @param sheet
     * @param consumer 完成数据转换后，调用 Consumer 进行数据处理
     */
    void readFromSheet(HSSFWorkbook workbook, HSSFSheet sheet, Consumer<D> consumer);
}
