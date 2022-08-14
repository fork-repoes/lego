package com.geekhalo.lego.core.excelasbean.support.reader.row;

import com.geekhalo.lego.core.excelasbean.support.reader.column.HSSFColumnToBeanPropertyWriter;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 将 Row 写入到 JavaBean
 */
public class HSSFRowToBeanWriter<D> {
    /**
     * 用于创建 JavaBean 对象
     */
    private final Supplier<D> supplier;
    private final HSSFHeaderParser headerParser;
    private final List<HSSFColumnToBeanPropertyWriter> columnToBeanPropertyWriters = Lists.newArrayList();
    /**
     * 显示标题
     */
    @Getter
    private final List<String> titles;

    public HSSFRowToBeanWriter(Supplier<D> supplier,
                               HSSFHeaderParser headerParser,
                               List<HSSFColumnToBeanPropertyWriter> columnToBeanPropertyWriters) {
        this.supplier = supplier;
        this.headerParser = headerParser;
        this.columnToBeanPropertyWriters.addAll(columnToBeanPropertyWriters);

        // 从 columnToBeanPropertyWriters 中收集展示标题
        this.titles = columnToBeanPropertyWriters.stream()
                .map(HSSFColumnToBeanPropertyWriter::getTitle)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 从 Header 中提取信息，形成 Path 与 Column Index 的对应关系
     * @param headerRow 标题行
     * @return
     */
    public Map<String, Integer> parsePathIndexMapFromHeader(HSSFRow headerRow) {
        Map<String, Integer> pathMap = Maps.newHashMap();
        for (int cell = 0; cell < headerRow.getLastCellNum(); cell++){
            HSSFCell cell1 = headerRow.getCell(cell);
            // 从标题中提取 path 信息
            String stringCellValue = cell1.getStringCellValue();
            String path = this.headerParser.toPathFromTitle(stringCellValue);

            pathMap.put(path, cell);
        }
        return pathMap;
    }

    public D writeToBean(Map<String, Integer> pathMap, HSSFRow row){
        // 1. 创建新对象
        D result = this.supplier.get();

        // 2. 依次遍历 HSSFColumnToBeanPropertyWriter， 将数据写入到 Bean 的属性
        for (HSSFColumnToBeanPropertyWriter columnToBeanWriter : columnToBeanPropertyWriters){
            // 2.1. 将 path 换算为 Cell Index
            String path = columnToBeanWriter.getPath();
            Integer index = pathMap.get(path);

            // 2.2. 将 Cell 中的数据写入的 Bean 的属性
            HSSFCell cell1 = index == null ? null : row.getCell(index);
            columnToBeanWriter.writeToBean(cell1, result);
        }

        return result;
    }
}
