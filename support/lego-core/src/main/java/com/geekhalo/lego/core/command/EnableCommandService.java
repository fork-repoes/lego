package com.geekhalo.lego.core.command;

import com.geekhalo.lego.core.command.support.CommandConfiguration;
import com.geekhalo.lego.core.command.support.CommandServiceBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CommandServiceBeanDefinitionRegistrar.class, CommandConfiguration.class})
public @interface EnableCommandService {
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
