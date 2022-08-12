package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellWriterContext;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFColumnWriterComposite implements HSSFColumnWriter{
    private final String path;
    private final int order;
    private final List<HSSFColumnWriter> columnWriters = Lists.newArrayList();
    public HSSFColumnWriterComposite(List<HSSFColumnWriter> writers, String path, int order){
        this.path = path;
        this.order = order;
        if(CollectionUtils.isNotEmpty(writers)){
            this.columnWriters.addAll(writers);
        }
        AnnotationAwareOrderComparator.sort(this.columnWriters);
    }

    @Override
    public void writeData(HSSFCellWriterContext context, Object data) {
        Object value = getValueFromPath(data);
        this.columnWriters.forEach(writer -> writer.writeData(context, value));
    }

    private Object getValueFromPath(Object data) {
        try {
            return FieldUtils.readField(data, this.path, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeHeader(HSSFCellWriterContext context) {
        this.columnWriters.forEach(writer -> writer.writeHeader(context));
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
