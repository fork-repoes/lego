package com.geekhalo.relation;

import com.geekhalo.lego.core.command.EnableCommandService;
import com.geekhalo.lego.core.query.EnableQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableCommandService(basePackages = "com.geekhalo.relation.app")
@EnableQueryService(basePackages = "com.geekhalo.relation.app")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);

        log.info("Spring Boot Started : {}", application);
    }
}
