package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface BeanPropertyWriterChainFactory {
    /***
     * 为 Field 创建 WriterChain
     * @param path
     * @param field
     * @return
     */
    BeanPropertyWriterChain createForField(String path, Field field);
}
