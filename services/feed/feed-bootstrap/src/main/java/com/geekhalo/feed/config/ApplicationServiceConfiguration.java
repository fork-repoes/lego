package com.geekhalo.feed.config;

import com.geekhalo.lego.core.command.EnableCommandService;
import com.geekhalo.lego.core.query.EnableQueryService;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCommandService(basePackages = "com.geekhalo.feed.app")
@EnableQueryService(basePackages = "com.geekhalo.feed.app")
public class ApplicationServiceConfiguration {
}
