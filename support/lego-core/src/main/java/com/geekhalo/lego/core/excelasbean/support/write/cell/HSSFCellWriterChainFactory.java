package com.geekhalo.lego.core.excelasbean.support.write.cell;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFCellWriterChainFactory {
    /**
     * 创建 Header 写入链
     * @param element
     * @param <D>
     * @return
     */
    <D>HSSFCellWriterChain<D> createHeaderWriterChain(AnnotatedElement element);

    /**
     * 创建 Data 写入链
     * @param element
     * @param <D>
     * @return
     */
    <D>HSSFCellWriterChain<D> createDataWriterChain(AnnotatedElement element);
}
