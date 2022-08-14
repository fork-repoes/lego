package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import com.geekhalo.lego.core.excelasbean.support.reader.cell.HSSFCellReader;
import com.google.common.base.Preconditions;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Bean 属性写入链
 */
public class BeanPropertyWriterChain {
    private final HSSFCellReader cellReader;
    private final BeanPropertyWriter beanWriter;

    public BeanPropertyWriterChain(HSSFCellReader cellReader,
                                   BeanPropertyWriter beanWriter) {

        Preconditions.checkArgument(cellReader != null);
        Preconditions.checkArgument(beanWriter != null);

        this.cellReader = cellReader;
        this.beanWriter = beanWriter;
    }

    public void writeToBean(HSSFCell cell, Object bean){
        // 从 Cell 中读取数据
        Object source = this.cellReader.readValue(cell);

        // 将数据写入到 Bean 属性
        this.beanWriter.writeToBean(bean, source);
    }

}
