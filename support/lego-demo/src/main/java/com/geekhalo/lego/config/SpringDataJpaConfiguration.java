package com.geekhalo.lego.config;

import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@EnableJpaRepositories(basePackages = {
        "com.geekhalo.lego.singlequery.jpa",
        "com.geekhalo.lego.validator",
        "com.geekhalo.lego.query",
        "com.geekhalo.lego.command"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
public class SpringDataJpaConfiguration {
}
