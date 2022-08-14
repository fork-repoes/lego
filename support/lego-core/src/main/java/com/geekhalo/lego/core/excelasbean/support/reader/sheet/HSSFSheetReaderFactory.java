package com.geekhalo.lego.core.excelasbean.support.reader.sheet;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 解析 class 信息，创建 HSSFSheetReader 对象
 */
public interface HSSFSheetReaderFactory {
    /**
     * 解析 Class 创建 HSSFSheetReader 对象
     * @param cls
     * @param <D>
     * @return
     */
    <D> HSSFSheetReader<D> createFor(Class<D> cls);
}
