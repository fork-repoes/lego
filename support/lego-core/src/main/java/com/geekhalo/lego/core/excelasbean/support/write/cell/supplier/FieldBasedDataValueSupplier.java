package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class FieldBasedDataValueSupplier implements HSSFValueSupplier {
    private final Field field;

    public FieldBasedDataValueSupplier(Field field) {
        this.field = field;
    }

    @Override
    public Object apply(Object o) {
        if (o == null){
            return null;
        }
        try {
            return FieldUtils.readField(field, o, true);
        } catch (IllegalAccessException e) {
            log.error("Failed to read field {} on {}", field, o, e);
        }
        return null;
    }
}
