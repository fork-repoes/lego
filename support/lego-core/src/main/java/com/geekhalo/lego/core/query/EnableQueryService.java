package com.geekhalo.lego.core.query;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(QueryServiceBeanDefinitionRegistrar.class)
public @interface EnableQueryService {
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    String queryImplementationPostfix() default "Impl";
}
