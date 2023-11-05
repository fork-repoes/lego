package com.geekhalo.lego.starter.web;

import com.geekhalo.lego.core.query.QueryServicesRegistry;
import com.geekhalo.lego.core.web.query.QueryDispatcherController;
import com.geekhalo.lego.core.web.query.QueryMethodRegistry;
import com.geekhalo.lego.core.web.query.QueryServicesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryWebConfiguration {
    @Bean
    public QueryDispatcherController queryDispatcherController(){
        return new QueryDispatcherController();
    }

    @Bean
    public QueryMethodRegistry queryMethodRegistry(){
        return new QueryMethodRegistry();
    }

    @Bean
    public QueryServicesProvider queryServicesProvider(){
        return new QueryServicesProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public QueryServicesRegistry queryServicesRegistry(){
        return new QueryServicesRegistry();
    }
}
