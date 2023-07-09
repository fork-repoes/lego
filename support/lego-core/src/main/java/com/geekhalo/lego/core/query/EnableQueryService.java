package com.geekhalo.lego.core.query;

import com.geekhalo.lego.core.query.support.QueryConfiguration;
import com.geekhalo.lego.core.query.support.QueryServiceBeanDefinitionRegistrar;
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
@Import({QueryServiceBeanDefinitionRegistrar.class, QueryConfiguration.class})
public @interface EnableQueryService {
    /**
     * 扫描包
     * @return
     */
    String[] basePackages() default {};

    /**
     * 自定义实现Bean后缀
     * @return
     */
    String customImplementationPostfix() default "Impl";
}
