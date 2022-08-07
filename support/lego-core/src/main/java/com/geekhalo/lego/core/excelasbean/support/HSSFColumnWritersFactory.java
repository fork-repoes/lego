package com.geekhalo.lego.core.excelasbean.support;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 */
public interface HSSFColumnWritersFactory {
    HSSFColumnWriters createFor(Class cls);
}
