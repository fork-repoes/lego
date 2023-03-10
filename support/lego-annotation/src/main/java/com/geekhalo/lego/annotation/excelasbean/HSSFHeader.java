package com.geekhalo.lego.annotation.excelasbean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记 表格头信息
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFHeader {
    /**
     * 标题
     * @return
     */
    String title();

    /**
     * 顺序
     * @return
     */
    int order() default 0;

    /**
     * 是否自适应大小
     * @return
     */
    boolean autoSizeColumn() default true;
}
