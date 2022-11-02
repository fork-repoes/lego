package com.geekhalo.lego.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringAopConfiguration {
}
