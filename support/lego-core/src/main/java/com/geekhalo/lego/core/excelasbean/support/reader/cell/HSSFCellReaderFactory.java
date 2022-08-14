package com.geekhalo.lego.core.excelasbean.support.reader.cell;

import com.geekhalo.lego.core.SmartComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFCellReaderFactory extends SmartComponent<Field> {
    HSSFCellReader createFor(String path, Field field);
}
