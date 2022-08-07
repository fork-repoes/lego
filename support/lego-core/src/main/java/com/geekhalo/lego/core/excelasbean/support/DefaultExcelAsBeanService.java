package com.geekhalo.lego.core.excelasbean.support;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFColumnWriters;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFColumnWritersFactory;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultExcelAsBeanService implements ExcelAsBeanService {
    private final Map<Class, HSSFColumnWriters> writersCache = Maps.newConcurrentMap();
    private final HSSFColumnWritersFactory writerFactory;

    public DefaultExcelAsBeanService(HSSFColumnWritersFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    @Override
    public <D> void writToSheet(HSSFSheet sheet, Class<D> dataCls, List<D> data) {
        HSSFColumnWriters hssfColumnWriters = this.writersCache.computeIfAbsent(dataCls,
                cls -> this.writerFactory.createFor(cls));
        hssfColumnWriters.write(sheet, data);
    }

    @Override
    public <D> void writTemplateToSheet(HSSFSheet sheet, Class<D> dataCls) {

    }

    @Override
    public <D> void readFromSheet(HSSFSheet sheet, Class<D> dataCls, Consumer<D> consumer) {

    }
}
