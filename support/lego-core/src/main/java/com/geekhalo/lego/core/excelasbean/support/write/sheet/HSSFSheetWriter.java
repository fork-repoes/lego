package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 向 Sheet 中写入数据
 */
public interface HSSFSheetWriter<D> {
    /**
     * 写入 Header 和 Data
     * @param context
     * @param sheet
     * @param data
     */
    void writeHeaderAndData(HSSFSheetContext context, HSSFSheet sheet, List<D> data);

    /**
     * 仅写入 Header
     * @param context
     * @param sheet
     */
    void writeHeader(HSSFSheetContext context, HSSFSheet sheet);

    /**
     * 仅写入数据
     * @param context
     * @param sheet
     * @param data
     */
    void writeData(HSSFSheetContext context, HSSFSheet sheet, List<D> data);
}
