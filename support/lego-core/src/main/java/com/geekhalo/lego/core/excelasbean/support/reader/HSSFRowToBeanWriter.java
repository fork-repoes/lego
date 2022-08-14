package com.geekhalo.lego.core.excelasbean.support.reader;

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
 */
public class HSSFRowToBeanWriter<D> {
    private final Supplier<D> supplier;
    private final HSSFHeaderParser headerParser = new DefaultHSSFHeaderParser();
    private final List<BeanPropertyWriterChain> initBeanPropertyWriterChains = Lists.newArrayList();
    private final Map<String, BeanPropertyWriterChain> pathWriterChain = Maps.newLinkedHashMap();
    @Getter
    private final List<String> titles;

    public HSSFRowToBeanWriter(Supplier<D> supplier,
                               List<HSSFColumnToBeanWriter> columnToBeanWriters) {
        this.supplier = supplier;
        for (HSSFColumnToBeanWriter columnToBeanWriter : columnToBeanWriters){
            pathWriterChain.put(columnToBeanWriter.getPath(), columnToBeanWriter.getPropertyWriterChain());
        }

        this.titles = columnToBeanWriters.stream()
                .map(HSSFColumnToBeanWriter::getTitle)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        columnToBeanWriters.stream()
                .filter(HSSFColumnToBeanWriter::isInit)
                .map(HSSFColumnToBeanWriter::getPropertyWriterChain)
                .forEach(beanPropertyWriterChain -> initBeanPropertyWriterChains.add(beanPropertyWriterChain));
    }

    public Map<Integer, String> parseIndexPathMapFromHeader(HSSFRow headerRow) {
        Map<Integer, String> pathMap = Maps.newHashMap();
        for (int cell = 0; cell < headerRow.getLastCellNum(); cell++){
            HSSFCell cell1 = headerRow.getCell(cell);
            String stringCellValue = cell1.getStringCellValue();
            String path = this.headerParser.toPathFromTitle(stringCellValue);
            pathMap.put(cell, path);
        }
        return pathMap;
    }

    public D writeToBean(Map<Integer, String> pathMap, HSSFRow row){
        D result = this.supplier.get();
        this.initBeanPropertyWriterChains
                .forEach(beanPropertyWriterChain -> beanPropertyWriterChain.writeToBean(null, result));
        for (int cell = 0; cell < row.getLastCellNum(); cell++){
            String path = pathMap.get(cell);
            HSSFCell cell1 = row.getCell(cell);
            pathWriterChain.get(path)
                    .writeToBean(cell1, result);
        }
        return result;
    }

//    private D createResult() {
//        try {
//            return ConstructorUtils.invokeConstructor(this.cls);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
