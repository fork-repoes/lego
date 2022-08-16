package com.geekhalo.lego.core.excelasbean.support.reader.sheet;

import com.geekhalo.lego.core.excelasbean.support.reader.row.HSSFRowToBeanWriter;
import com.google.common.base.Preconditions;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 */
public class DefaultHSSFSheetReader<D> implements HSSFSheetReader<D> {
    /**
     * 待转化的 JavaBean 类型
     */
    private final Class<D> cls;

    /**
     * 将 Row 数据写入到 JavaBean
     */
    private final HSSFRowToBeanWriter<D> rowToBeanWriter;

    public DefaultHSSFSheetReader(Class<D> cls,
                                  HSSFRowToBeanWriter<D> rowToBeanWriter) {
        Preconditions.checkArgument(cls != null);
        Preconditions.checkArgument(rowToBeanWriter != null);

        this.cls = cls;
        this.rowToBeanWriter = rowToBeanWriter;
    }

    @Override
    public void writeTemplate(HSSFWorkbook workbook, HSSFSheet sheet) {
        // 创建 Header 行
        HSSFRow row = sheet.createRow(0);

        // 写入模板数据
        int index = 0;
        for (String title : this.rowToBeanWriter.getTitles()){
            // 设置默认颜色
            setDefaultStyle(workbook, sheet, index);

            // 设置 Header 信息
            HSSFCell cell = row.createCell(index++);
            cell.setCellValue(title);

            // 设置 Header 样式
            setHeaderStyle(workbook, cell);
        }
        // 开启保护，避免 Header 被修改
        sheet.protectSheet(cls.getName());
    }



    @Override
    public void readFromSheet(HSSFWorkbook workbook, HSSFSheet sheet, Consumer<D> consumer) {
        // 从 Header 中获取 Path 和 Index 的映射
        Map<String, Integer> indexPathMap = this.rowToBeanWriter.parsePathIndexMapFromHeader(sheet.getRow(0));

        // 遍历每一行，读取数据
        int lastRow = sheet.getLastRowNum();
        for (int i = 1;i <= lastRow; i++){
            // 获取当前行
            HSSFRow row = sheet.getRow(i);

            // 进行数据转换
            D result = this.rowToBeanWriter.writeToBean(indexPathMap, row);

            // 消费数据
            consumer.accept(result);
        }
    }

    private void setHeaderStyle(HSSFWorkbook workbook, HSSFCell cell) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cell.setCellStyle(cellStyle);
        // 锁定 Header
        cellStyle.setLocked(true);
        // 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        HSSFFont font = workbook.createFont();
        cellStyle.setFont(font);
        // 设置为加粗
        font.setBold(true);
    }

    private void setDefaultStyle(HSSFWorkbook workbook, HSSFSheet sheet, int index) {
        // 自适应大小
        sheet.autoSizeColumn(index);
        sheet.setColumnWidth(index, sheet.getColumnWidth(index) * 3);

        // 未锁定
        HSSFCellStyle lockedCellStyle = workbook.createCellStyle();
        lockedCellStyle.setLocked(false);
        sheet.setDefaultColumnStyle(index, lockedCellStyle);
    }

}
