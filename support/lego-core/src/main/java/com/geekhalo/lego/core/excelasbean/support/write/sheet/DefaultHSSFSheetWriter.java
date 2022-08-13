package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriter;
import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriterContext;
import com.google.common.base.Preconditions;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 执行实际写入操作，包括： <br />
 * 1. 写入 Header <br />
 * 2. 写入 Data <br />
 */
public class DefaultHSSFSheetWriter<D> implements HSSFSheetWriter<D>{
    private final HSSFRowWriter rowWriter;

    public DefaultHSSFSheetWriter(HSSFRowWriter rowWriter) {
        Preconditions.checkArgument(rowWriter != null);
        this.rowWriter = rowWriter;
    }


    @Override
    public void writeHeaderAndData(HSSFSheetContext context, HSSFSheet sheet, List<D> data) {
        writeHeader(context, sheet);
        writeData(context, sheet, data);
    }

    @Override
    public void writeHeader(HSSFSheetContext context, HSSFSheet sheet) {
        HSSFRowWriterContext headWriterContext = HSSFRowWriterContext.builder()
                .workbook(context.getWorkbook())
                .sheet(sheet)
                .build();
        this.rowWriter.writeHeaderRow(headWriterContext);
    }

    @Override
    public void writeData(HSSFSheetContext context, HSSFSheet sheet, List<D> data) {
        HSSFRowWriterContext dataWriterContext = HSSFRowWriterContext.builder()
                .workbook(context.getWorkbook())
                .sheet(sheet)
                .build();
        data.forEach(d -> this.rowWriter.writeDataRow(dataWriterContext, d));
    }
}
