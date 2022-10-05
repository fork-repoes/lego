package com.geekhalo.lego.core.command;

import java.lang.annotation.*;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 标记该 Bean 非 CommandService，不会生成 proxy 对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NoCommandService {
}
