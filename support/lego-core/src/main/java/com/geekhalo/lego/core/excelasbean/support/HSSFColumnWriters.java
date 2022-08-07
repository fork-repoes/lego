package com.geekhalo.lego.core.excelasbean.support;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入一列数据，包括数据头，和数据信息
 */
public class HSSFColumnWriters<D> {
    private final List<HSSFColumnWriter<D>> writers = Lists.newArrayList();
    public HSSFColumnWriters(List<HSSFColumnWriter<D>> writers){
        this.writers.addAll(writers);
        AnnotationAwareOrderComparator.sort(this.writers);
    }

    public void write(HSSFSheet sheet, List<D> datas){
        writeHeader(sheet);
        writeData(sheet, datas);
    }

    private void writeHeader(HSSFSheet sheet){
        HSSFRow row = sheet.createRow(0);
        for (int index = 0; index < writers.size(); index ++){
            HSSFCell cell = row.createCell(index);
            writers.get(index).writeHeader(cell);
        }
    }

    private void writeData(HSSFSheet sheet, List<D> datas){
        for (D d : datas) {
            int lastRow = sheet.getLastRowNum();
            HSSFRow row = sheet.createRow(lastRow + 1);
            for (int index = 0; index < writers.size(); index ++){
                HSSFCell cell = row.createCell(index);
                writers.get(index).writeData(cell, d);
            }
        }
    }
}
