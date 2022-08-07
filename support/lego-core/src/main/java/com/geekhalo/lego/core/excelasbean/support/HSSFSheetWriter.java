package com.geekhalo.lego.core.excelasbean.support;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;
/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 向 Sheet 中写入数据
 */
public class HSSFSheetWriter {
    private final HSSFSheet sheet;

    public HSSFSheetWriter(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    public <D>void write(List<HSSFColumnWriter<D>> writers, List<D> datas){
        writeHeader(writers);
        writeData(writers, datas);
    }

    private void writeHeader(List<? extends HSSFHeaderWriter> writers){
        HSSFRow row = this.sheet.createRow(0);
        for (int index = 0; index < writers.size(); index ++){
            HSSFCell cell = row.createCell(index);
            writers.get(index).writeHeader(cell);
        }
    }

    private  <D> void writeData(List<? extends HSSFDataWriter<D>> writers, List<D> datas){
        for (D d : datas) {
            int lastRow = sheet.getLastRowNum();
            HSSFRow row = this.sheet.createRow(lastRow + 1);
            for (int index = 0; index < writers.size(); index ++){
                HSSFCell cell = row.createCell(index);
                writers.get(index).writeData(cell, d);
            }
        }
    }

    public HSSFSheet getSheet(){
        return this.sheet;
    }

}
