package com.geekhalo.lego.core.excelasbean.support.reader;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFSheetReader<D> implements HSSFSheetReader<D>{
    private final Class<D> cls;
    private final HSSFRowToBeanWriter<D> rowToBeanWriter;

    public DefaultHSSFSheetReader(Class<D> cls,
                                  HSSFRowToBeanWriter<D> rowToBeanWriter) {
        this.cls = cls;
        this.rowToBeanWriter = rowToBeanWriter;
    }

    @Override
    public void writeTemplate(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        int index = 0;
        for (String title : this.rowToBeanWriter.getTitles()){
            setDefaultStyle(workbook, sheet, index);

            HSSFCell cell = row.createCell(index++);

            cell.setCellValue(title);

            setHeaderStyle(workbook, cell);
        }
        sheet.protectSheet("123456");
    }

    private void setHeaderStyle(HSSFWorkbook workbook, HSSFCell cell) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLocked(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
    }

    private void setDefaultStyle(HSSFWorkbook workbook, HSSFSheet sheet, int index) {
        sheet.autoSizeColumn(index);
        sheet.setColumnWidth(index, sheet.getColumnWidth(index) * 20 / 10);
        HSSFCellStyle lockedCellStyle = workbook.createCellStyle();
        lockedCellStyle.setLocked(false);
        sheet.setDefaultColumnStyle(index, lockedCellStyle);
    }

    @Override
    public void readFromSheet(HSSFWorkbook workbook, HSSFSheet sheet, Consumer<D> consumer) {

        Map<Integer, String> indexPathMap = this.rowToBeanWriter.parseIndexPathMapFromHeader(sheet.getRow(0));

        int lastRow = sheet.getLastRowNum();
        for (int i = 1;i <= lastRow; i++){
            HSSFRow row = sheet.getRow(i);

            D result = this.rowToBeanWriter.writeToBean(indexPathMap, row);

            consumer.accept(result);
        }
    }

}
