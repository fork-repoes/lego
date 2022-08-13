package com.geekhalo.lego.core.excelasbean.support.write.sheet;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * HSSFSheetWriter 工厂
 */
public interface HSSFSheetWriterFactory {
    /**
     * 创建 HSSFSheetWriter
     * @param cls
     * @param <D>
     * @return
     */
    <D> HSSFSheetWriter<D> createFor(Class<D> cls);
}
