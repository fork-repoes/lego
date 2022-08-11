package com.geekhalo.lego.core.excelasbean.support.write.row;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFRowWriterFactory {
    <D> HSSFRowWriter<D> create(Class<D> cls);
}
