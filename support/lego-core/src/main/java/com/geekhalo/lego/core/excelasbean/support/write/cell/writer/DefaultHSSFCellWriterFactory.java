package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(Integer.MAX_VALUE)
public class DefaultHSSFCellWriterFactory implements HSSFHeaderCellWriterFactory, HSSFDataCellWriterFactory {
    @Override
    public HSSFCellWriter create(AnnotatedElement annotatedElement) {
        return new DefaultHSSFCellWriter();
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return true;
    }
}
