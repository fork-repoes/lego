package com.geekhalo.lego.core.excelasbean.support.reader;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface BeanPropertyWriter {
    public void writeToBean(Object bean, Object value);
}
