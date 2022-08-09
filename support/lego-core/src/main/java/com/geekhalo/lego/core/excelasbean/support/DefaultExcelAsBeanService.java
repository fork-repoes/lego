package com.geekhalo.lego.core.excelasbean.support;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetContext;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriter;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriterFactory;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultExcelAsBeanService implements ExcelAsBeanService {
    private final Map<Class, HSSFSheetWriter> writersCache = Maps.newConcurrentMap();
    private final HSSFSheetWriterFactory writerFactory;

    public DefaultExcelAsBeanService(HSSFSheetWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    @Override
    public <D> void writToSheet(HSSFWorkbook workbook, String sheetName, Class<D> dataCls, List<D> data) {
        HSSFSheet sheet = workbook.createSheet(sheetName);

        HSSFSheetWriter<D> hssfSheetWriter = this.writersCache.computeIfAbsent(dataCls,
                cls -> this.writerFactory.createFor(cls));

        HSSFSheetContext context = HSSFSheetContext.<D>builder()
                .workbook(workbook)
                .build();

        hssfSheetWriter.write(context, sheet, data);
    }

    @Override
    public <D> void writTemplateToSheet(HSSFSheet sheet, Class<D> dataCls) {

    }

    @Override
    public <D> void readFromSheet(HSSFSheet sheet, Class<D> dataCls, Consumer<D> consumer) {

    }
}
