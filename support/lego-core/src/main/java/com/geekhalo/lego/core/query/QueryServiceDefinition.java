package com.geekhalo.lego.core.query;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 标记一个接口为 QueryService，系统自动生成实现的代理对象
 */
@Indexed
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QueryServiceDefinition {

    Class domainClass();

    Class repositoryClass();
}
