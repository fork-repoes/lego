package com.geekhalo.lego.config;

import com.geekhalo.lego.core.command.EnableCommandService;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@EnableCommandService(basePackages = "com.geekhalo.lego.command")
public class CommandServiceConfiguration {
}
