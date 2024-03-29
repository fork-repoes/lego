package com.geekhalo.feed.config;

import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.geekhalo.feed.infra",
        repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class
)
public class JpaConfiguration {
}
