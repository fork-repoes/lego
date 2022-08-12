package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataDataSupplierFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
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
public class FieldBasedDataDataSupplierFactory implements HSSFDataDataSupplierFactory {
    @Override
    public HSSFDataSupplier create(AnnotatedElement annotatedElement, String name) {
        Field field = (Field) annotatedElement;
        return o -> {
            if (o == null){
                return null;
            }
            try {
                return FieldUtils.readField(field, o, true);
            } catch (IllegalAccessException e) {
                log.error("Failed to read field {} on {}", field, o, e);
            }
            return null;
        };
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Field;
    }
}
