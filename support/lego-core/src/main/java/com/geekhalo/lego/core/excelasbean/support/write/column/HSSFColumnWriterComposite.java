package com.geekhalo.lego.core.excelasbean.support.write.column;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * HSSFColumnWriter 组合，用于写入一组 Column
 */
public class HSSFColumnWriterComposite implements HSSFColumnWriter{
    // 数据转换器
    private final Function converter;
    // 顺序
    private final int order;
    // 组合内的写入器
    private final List<HSSFColumnWriter> columnWriters = Lists.newArrayList();

    public HSSFColumnWriterComposite(List<HSSFColumnWriter> writers, Function converter, int order){
        Preconditions.checkArgument(converter != null);
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(writers));

        this.converter = converter;
        this.order = order;

        this.columnWriters.addAll(writers);

        // 根据 order 进行排序
        AnnotationAwareOrderComparator.sort(this.columnWriters);
    }

    @Override
    public void writeDataCell(HSSFCellWriterContext context, Object data) {
        // 数据转换
        Object value = this.converter.apply(data);

        // 写入 DataCell
        this.columnWriters.forEach(writer -> writer.writeDataCell(context, value));
    }


    @Override
    public void writeHeaderCell(HSSFCellWriterContext context) {
        this.columnWriters.forEach(writer -> writer.writeHeaderCell(context));
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
