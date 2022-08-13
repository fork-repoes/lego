package com.geekhalo.lego.core.excelasbean.support.write.row;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 创建行写入器
 */
public interface HSSFRowWriterFactory {
    /**
     * 创建行写入器
     * @param cls
     * @param <D>
     * @return
     */
    <D> HSSFRowWriter<D> create(Class<D> cls);
}
