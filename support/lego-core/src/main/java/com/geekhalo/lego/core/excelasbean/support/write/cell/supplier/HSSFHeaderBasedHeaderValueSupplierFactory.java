package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(Integer.MAX_VALUE)
public class HSSFHeaderBasedHeaderValueSupplierFactory implements HSSFHeaderValueSupplierFactory {
    @Override
    public HSSFValueSupplier create(AnnotatedElement element) {
        String title = AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeader.class)
                .title();
        return new FixValueSupplier(title);
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFHeader.class);
    }
}
