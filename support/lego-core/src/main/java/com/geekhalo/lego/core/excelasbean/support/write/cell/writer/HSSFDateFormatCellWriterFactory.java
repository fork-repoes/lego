package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import com.geekhalo.lego.annotation.excelasbean.HSSFDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/12.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 处理 HSSFDateFormat 注解
 */
@Order(1)
@Slf4j
public class HSSFDateFormatCellWriterFactory implements HSSFDataCellWriterFactory {

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFDateFormat.class);
    }

    @Override
    public HSSFCellWriter create(AnnotatedElement element) {
        HSSFDateFormat dateFormat = AnnotatedElementUtils.findMergedAnnotation(element, HSSFDateFormat.class);
        return new DateFormatHSSFCellWriter(dateFormat.value(), element);
    }
}
