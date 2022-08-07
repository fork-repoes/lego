package com.geekhalo.lego.core.excelasbean.support.write.data;

import com.geekhalo.lego.core.SmartComponent;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFDataConverterFactory extends SmartComponent<Field> {
    boolean support(Field field);

    HSSFDataConverter create(Field field);
}
