package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataSupplier;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFHeaderDataSupplierFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(Integer.MAX_VALUE)
public class HSSFHeaderBasedHeaderDataSupplierFactory implements HSSFHeaderDataSupplierFactory {
    @Override
    public HSSFDataSupplier create(AnnotatedElement element) {
        return t -> AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeader.class)
                .value();
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFHeader.class);
    }
}
