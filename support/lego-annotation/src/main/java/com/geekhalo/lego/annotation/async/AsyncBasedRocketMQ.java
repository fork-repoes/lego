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
    /**
     * MQ topic
     * @return
     */
    String topic();

    /**
     * MQ tag
     * @return
     */
    String tag();

    /**
     * 消费组
     * @return
     */
    String consumerGroup();

    /**
     * nameServer 配置
     * @return
     */
    String nameServer() default "${rocketmq.name-server:}";

    /**
     * 消费者运行的 profile，主要用于发送和消费分离的场景
     * @return
     */
    String consumerProfile() default "";
}