package com.geekhalo.lego;


import com.geekhalo.lego.core.command.EnableCommandService;
import com.geekhalo.lego.core.query.EnableQueryService;
import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }
}
