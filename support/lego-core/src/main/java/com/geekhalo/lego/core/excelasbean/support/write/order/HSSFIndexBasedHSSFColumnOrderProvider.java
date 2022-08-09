package com.geekhalo.lego.core.excelasbean.support.write.order;

import com.geekhalo.lego.annotation.excelasbean.HSSFIndex;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFIndexBasedHSSFColumnOrderProvider implements HSSFColumnOrderProvider{
    @Override
    public boolean support(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, HSSFIndex.class);
    }

    @Override
    public int orderForColumn(Field field) {
        return AnnotatedElementUtils.findMergedAnnotation(field, HSSFIndex.class)
                .value();
    }
}
