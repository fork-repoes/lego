package com.geekhalo.lego;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = "com.geekhalo.lego.singlequery.mybatis")
@EnableJpaRepositories(basePackages = {
        "com.geekhalo.lego.singlequery.jpa",
        "com.geekhalo.lego.validator"
})
public class DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }
}
