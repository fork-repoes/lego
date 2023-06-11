package com.geekhalo.like;

import com.geekhalo.lego.core.command.EnableCommandService;
import com.geekhalo.lego.core.query.EnableQueryService;
import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import com.geekhalo.lego.starter.singlequery.JpaBasedSingleQueryConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableCommandService(basePackages = "com.geekhalo.like.app")
@EnableQueryService(basePackages = "com.geekhalo.like.app")
public class LikeApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(LikeApplication.class, args);

        log.info("Spring Boot Started : {}", application);
    }
}
