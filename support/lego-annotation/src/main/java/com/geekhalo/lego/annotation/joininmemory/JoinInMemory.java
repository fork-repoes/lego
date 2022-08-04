package com.geekhalo.lego.annotation.joininmemory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/8/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinInMemory {
}
