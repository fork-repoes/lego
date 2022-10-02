package com.geekhalo.lego;


import com.geekhalo.lego.core.query.EnableQueryService;
import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
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
        "com.geekhalo.lego.validator",
        "com.geekhalo.lego.query",
        "com.geekhalo.lego.command"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
@EnableQueryService(basePackages = "com.geekhalo.lego.query")
public class DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }
}
