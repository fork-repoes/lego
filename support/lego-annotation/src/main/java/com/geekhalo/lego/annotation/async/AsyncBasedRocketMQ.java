package com.geekhalo.lego.annotation.async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncBasedRocketMQ {
    String topic();

    String tag();

    String consumerGroup();

    String nameServer() default "${rocketmq.name-server:}";

    String consumerProfile() default "";
}