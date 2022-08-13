package com.geekhalo.lego.annotation.excelasbean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/8/12.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 标记问嵌入对象，将嵌入对象中的数据进行展开处理
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFEmbedded {
}
