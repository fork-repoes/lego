package com.geekhalo.lego.core.query;

import java.lang.annotation.*;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 标记该 Bean 非 QueryService，不会生成 proxy 对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NoQueryService {
}
