package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriter;
import com.geekhalo.lego.core.excelasbean.support.write.row.HSSFRowWriterContext;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFSheetWriter<D> implements HSSFSheetWriter<D>{
    private final HSSFRowWriter rowWriter;

    public DefaultHSSFSheetWriter(HSSFRowWriter rowWriter) {
        this.rowWriter = rowWriter;
    }


    @Override
    public void write(HSSFSheetContext context, HSSFSheet sheet,  List<D> data) {
        HSSFRowWriterContext headWriterContext = HSSFRowWriterContext.builder()
                .workbook(context.getWorkbook())
                .sheet(sheet)
                .build();
        this.rowWriter.writeHead(headWriterContext);

        for (D d : data){
            HSSFRowWriterContext dataWriterContext = HSSFRowWriterContext.builder()
                    .workbook(context.getWorkbook())
                    .sheet(sheet)
                    .build();
            this.rowWriter.writeData(dataWriterContext, d);
        }
    }
}
