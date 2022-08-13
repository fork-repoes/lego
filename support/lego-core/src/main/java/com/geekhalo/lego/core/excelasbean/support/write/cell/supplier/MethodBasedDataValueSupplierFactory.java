package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class MethodBasedDataValueSupplierFactory implements HSSFDataValueSupplierFactory {
    @Override
    public HSSFValueSupplier create(AnnotatedElement annotatedElement) {
        return new MethodBasedValueSupplier((Method) annotatedElement);
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Method;
    }
}
