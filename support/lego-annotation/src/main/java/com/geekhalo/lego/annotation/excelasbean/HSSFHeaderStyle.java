package com.geekhalo.lego.annotation.excelasbean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 * 表头样式
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFHeaderStyle {
    /**
     * 表头样式
     * @return
     */
    String value();
}
