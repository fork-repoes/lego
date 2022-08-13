package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
@Order(Integer.MAX_VALUE)
public class FieldBasedDataValueSupplierFactory implements HSSFDataValueSupplierFactory {
    @Override
    public HSSFValueSupplier create(AnnotatedElement annotatedElement) {
        Field field = (Field) annotatedElement;
        return new FieldBasedDataValueSupplier(field);
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Field;
    }
}
