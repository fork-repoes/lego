package com.geekhalo.tinyurl.config;

import com.geekhalo.lego.core.command.EnableCommandService;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCommandService(
        basePackages = "com.geekhalo.tinyurl.app"
)
public class ApplicationConfiguration {
}
