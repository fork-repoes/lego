package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import lombok.Builder;
import lombok.Value;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 * 
 */
@Value
@Builder
public class HSSFCellWriterContext {
    private final HSSFWorkbook workbook;
    private final HSSFSheet sheet;
    private final HSSFRow row;
}
