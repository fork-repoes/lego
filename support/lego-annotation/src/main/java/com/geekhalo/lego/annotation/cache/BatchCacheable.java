package com.geekhalo.lego.annotation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchCacheable {
    /**
     * 缓存名称
     */
    String value() default "";

    /**
     * 缓存管理器名称
     * @return
     */
    String cacheManager() default "";

    /**
     * SpEL 表达式，用于构建key
     * 键前缀，用于区分不同业务类型
     */
    String key() default "";

    /**
     * 过期时间（秒）
     */
    int expire() default 1000 * 60 * 5;


    /**
     * 批量查询分批大小（若查询集合太大，可以适当分批查询）
     */
    int batchSize() default 100;
}
