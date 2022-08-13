package com.geekhalo.lego.core.excelasbean.support.write.order;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 * 从 HSSFIndex 中获取 index 信息
 */
public class HSSFHeaderBasedColumnOrderProvider implements HSSFColumnOrderProvider {
    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFHeader.class);
    }

    @Override
    public int orderForColumn(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.findMergedAnnotation(annotatedElement, HSSFHeader.class)
                .order();
    }
}
