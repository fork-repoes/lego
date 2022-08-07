package com.geekhalo.lego.core.excelasbean.support.write.data;

import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFWriterChain;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFDataWriterChain extends HSSFWriterChain {
    private final HSSFDataWriter dataWriter;
    private final List<HSSFDataConverter> dataConverters = Lists.newArrayList();

    public HSSFDataWriterChain(List<HSSFCellConfigurator> cellConfigs,
                               List<HSSFDataConverter> dataConverters,
                               HSSFDataWriter dataWriter) {
        super(cellConfigs);
        this.dataConverters.addAll(dataConverters);
        AnnotationAwareOrderComparator.sort(this.dataConverters);
        this.dataWriter = dataWriter;
    }

    public void write(HSSFCell cell, Object data) {
        configForCell(cell);
        Object value = convertData(data);
        this.dataWriter.writeData(cell, value);
    }

    private Object convertData(Object data) {
        Object tmp = data;
        for (HSSFDataConverter converter : dataConverters){
            if (tmp == null){
                return null;
            }
            tmp = converter.convert(tmp);
        }
        return tmp;
    }

}
