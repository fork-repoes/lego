package com.geekhalo.lego.annotation.faultrecovery.smart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartFault {
    String recover() default "";

    int maxRetry() default 3;

    /**
     * 需要重试的异常信息
     * @return
     */
    Class<? extends Throwable>[] include() default {};

    /**
     * 不需要重试的异常信息
     * @return
     */
    Class<? extends Throwable>[] exclude() default {};
}
