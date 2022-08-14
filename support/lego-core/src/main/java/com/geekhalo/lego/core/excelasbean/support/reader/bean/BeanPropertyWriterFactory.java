package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import com.geekhalo.lego.core.SmartComponent;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface BeanPropertyWriterFactory extends SmartComponent<Field> {

    BeanPropertyWriter createFor(String path, Field field);
}
