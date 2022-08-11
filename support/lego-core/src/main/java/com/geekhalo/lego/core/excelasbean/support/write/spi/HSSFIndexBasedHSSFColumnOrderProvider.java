package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.annotation.excelasbean.HSSFIndex;
import com.geekhalo.lego.core.excelasbean.support.write.order.HSSFColumnOrderProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFIndexBasedHSSFColumnOrderProvider implements HSSFColumnOrderProvider {
    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFIndex.class);
    }

    @Override
    public int orderForColumn(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.findMergedAnnotation(annotatedElement, HSSFIndex.class)
                .value();
    }
}
