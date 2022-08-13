package com.geekhalo.lego.core.excelasbean;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Bean 转换入口类
 */
public interface ExcelAsBeanService {
    /**
     * 将 D 写入到 Sheet
     * @param workbook
     * @param sheetName 待写入的 Sheet名称
     * @param dataCls 待写入的 Class
     * @param data 待写入数据
     * @param <D>
     */
    <D> void writHeaderAndDataToSheet(HSSFWorkbook workbook, String sheetName, Class<D> dataCls, List<D> data);

    /**
     * 写入数据
     * @param workbook
     * @param sheetName
     * @param dataCls
     * @param data
     * @param <D>
     */
    <D> void writDataToSheet(HSSFWorkbook workbook, String sheetName, Class<D> dataCls, List<D> data);


    /**
     * 将 Excel 模板写入到 Sheet
     * @param sheet 待写入的 sheet
     * @param dataCls 待写入数据
     * @param <D>
     */
    <D> void writTemplateToSheet(HSSFSheet sheet, Class<D> dataCls);

    /**
     * 从 Sheet 中读取数据
     * @param sheet 待读取数据的 Sheet
     * @param dataCls 待读取数据的类型
     * @param <D>
     */
    default <D> List<D> readFromSheet(HSSFSheet sheet, Class<D> dataCls){
        List<D> result = Lists.newArrayList();
        readFromSheet(sheet, dataCls, d -> result.add(d));
        return result;
    }

    /**
     * 从 Sheet 中读取数据
     * @param sheet 待读取数据的 Sheet
     * @param dataCls 待读取数据的类型
     * @param consumer 回调器，完成数据解析后，直接调用回调器
     * @param <D>
     */
    <D> void readFromSheet(HSSFSheet sheet, Class<D> dataCls, Consumer<D> consumer);
}
