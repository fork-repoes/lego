package com.geekhalo.lego.core.excelasbean.support;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.reader.HSSFSheetReader;
import com.geekhalo.lego.core.excelasbean.support.reader.HSSFSheetReaderFactory;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetContext;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriter;
import com.geekhalo.lego.core.excelasbean.support.write.sheet.HSSFSheetWriterFactory;
import com.google.common.base.Preconditions;
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
 *
 * ExcelAsBeanService 默认实现<br />
 * 屏蔽内部结构的复杂性
 */
public class DefaultExcelAsBeanService implements ExcelAsBeanService {
    /**
     * HSSFSheetWriter 缓存，解析一次，运行多次
     */
    private final Map<Class, HSSFSheetWriter> writersCache = Maps.newConcurrentMap();

    /**
     * HSSFSheetWriter 工厂，从 class 中读取信息，构建 HSSFSheetWriter 对象
     */
    private final HSSFSheetWriterFactory writerFactory;


    /**
     * HSSFSheetReader 缓存，解析一次，运行多次
     */
    private final Map<Class, HSSFSheetReader> readersCache = Maps.newConcurrentMap();

    /**
     * HSSFSheetReader 工厂，从 class 中解析信息，构建 HSSFSheetReader 对象
     */
    private final HSSFSheetReaderFactory readerFactory;


    public DefaultExcelAsBeanService(HSSFSheetWriterFactory writerFactory,
                                     HSSFSheetReaderFactory readerFactory) {

        Preconditions.checkArgument(writerFactory != null);
        Preconditions.checkArgument(readerFactory != null);

        this.writerFactory = writerFactory;
        this.readerFactory = readerFactory;
    }

    @Override
    public <D> void writHeaderAndDataToSheet(HSSFWorkbook workbook, String sheetName, Class<D> dataCls, List<D> data) {
        // 创建 Sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 获取 HSSFSheetWriter 对象
        HSSFSheetWriter<D> sheetWriter = this.writersCache.computeIfAbsent(dataCls,
                cls -> this.writerFactory.createFor(cls));

        // 构建 Context 对象
        HSSFSheetContext context = HSSFSheetContext.<D>builder()
                .workbook(workbook)
                .build();
        // 写入数据
        sheetWriter.writeHeaderAndData(context, sheet, data);
    }

    @Override
    public <D> void writDataToSheet(HSSFWorkbook workbook, String sheetName, Class<D> dataCls, List<D> data) {
        HSSFSheet sheet = workbook.getSheet(sheetName);

        // 获取 HSSFSheetWriter 对象
        HSSFSheetWriter<D> sheetWriter = this.writersCache.computeIfAbsent(dataCls,
                cls -> this.writerFactory.createFor(cls));

        // 构建 Context 对象
        HSSFSheetContext context = HSSFSheetContext.<D>builder()
                .workbook(workbook)
                .build();
        // 写入数据
        sheetWriter.writeData(context, sheet, data);
    }

    @Override
    public <D> void writTemplateToSheet(HSSFSheet sheet, Class<D> dataCls) {
        HSSFSheetReader<D> sheetReader = this.readersCache.computeIfAbsent(dataCls,
                cls -> this.readerFactory.createFor(cls));

        sheetReader.writeTemplate(sheet.getWorkbook(), sheet);
    }

    @Override
    public <D> void readFromSheet(HSSFSheet sheet, Class<D> dataCls, Consumer<D> consumer) {
        HSSFSheetReader<D> sheetReader = this.readersCache.computeIfAbsent(dataCls,
                cls -> this.readerFactory.createFor(cls));

        sheetReader.readFromSheet(sheet.getWorkbook(), sheet, consumer);
    }
}
