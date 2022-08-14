package com.geekhalo.lego.core.excelasbean.support.reader;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class BeanPropertyWriterChain {
    private final HSSFCellReader cellReader;
    private final List<HSSFCellDataConverter> dataConverters = Lists.newArrayList();
    private final BeanPropertyWriter beanWriter;

    public BeanPropertyWriterChain(HSSFCellReader cellReader,
                                   List<HSSFCellDataConverter> dataConverters,
                                   BeanPropertyWriter beanWriter) {

        Preconditions.checkArgument(cellReader != null);
        Preconditions.checkArgument(beanWriter != null);

        this.cellReader = cellReader;
        this.beanWriter = beanWriter;
        if (CollectionUtils.isNotEmpty(dataConverters)){
            this.dataConverters.addAll(dataConverters);
        }
    }

    public void writeToBean(HSSFCell cell, Object bean){
        Object source = this.cellReader.readValue(cell);

        Object value = convert(source);

        this.beanWriter.writeToBean(bean, value);
    }

    private Object convert(Object source) {
        Object tmp = source;
        for (HSSFCellDataConverter converter : this.dataConverters){
            tmp = converter.convert(tmp);
        }
        return tmp;
    }
}
