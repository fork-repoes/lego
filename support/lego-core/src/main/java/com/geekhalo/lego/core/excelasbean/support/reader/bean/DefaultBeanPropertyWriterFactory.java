package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(Integer.MAX_VALUE)
public class DefaultBeanPropertyWriterFactory implements BeanPropertyWriterFactory{
    @Override
    public BeanPropertyWriter createFor(String path, Field field) {
        return new DefaultBeanPropertyWriter(path);
    }

    @Override
    public boolean support(Field field) {
        return true;
    }
}
