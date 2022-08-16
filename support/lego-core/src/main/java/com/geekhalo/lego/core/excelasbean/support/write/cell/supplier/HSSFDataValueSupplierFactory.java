package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import com.geekhalo.lego.core.SmartComponent;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFDataValueSupplierFactory extends SmartComponent<AnnotatedElement> {
    HSSFValueSupplier create(AnnotatedElement annotatedElement);
}
