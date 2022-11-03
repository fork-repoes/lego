package com.geekhalo.lego.annotation.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 执行器名称
     * @return
     */
    String executorName() default "DEFAULT_EXECUTOR";


    /**
     * 业务组，和 key 构成唯一标识
     * @return
     */
    int group();

    /**
     * 幂等key，通过 SpEL 表达式读取参数信息
     * @return
     */
    String key();

    /**
     * 幂等结果执行类型
     * @return
     */
    IdempotentHandleType handleType() default IdempotentHandleType.RESULT;
}
