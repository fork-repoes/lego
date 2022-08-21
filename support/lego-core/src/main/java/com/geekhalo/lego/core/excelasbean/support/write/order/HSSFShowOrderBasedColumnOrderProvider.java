package com.geekhalo.lego.core.excelasbean.support.write.order;

import com.geekhalo.lego.annotation.excelasbean.HSSFShowOrder;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 * 从 HSSFShowOrder 中获取 index 信息
 */
public class HSSFShowOrderBasedColumnOrderProvider implements HSSFColumnOrderProvider {
    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFShowOrder.class);
    }

    @Override
    public int orderForColumn(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.findMergedAnnotation(annotatedElement, HSSFShowOrder.class)
                .value();
    }
}
