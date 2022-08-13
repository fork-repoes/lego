package com.geekhalo.lego.core.excelasbean.support.write.column;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFColumnWriterFactory {
    boolean support(Method method);

    <D> HSSFColumnWriter<D> createForGetter(Method getter);

    boolean support(Field field);
    <D> HSSFColumnWriter<D> createForField(Field field);
}
