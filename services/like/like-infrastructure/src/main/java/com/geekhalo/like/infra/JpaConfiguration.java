package com.geekhalo.like.infra;

import com.geekhalo.lego.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class
)
public class JpaConfiguration {
}
