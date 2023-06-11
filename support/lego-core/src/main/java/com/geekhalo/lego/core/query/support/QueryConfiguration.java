package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.query.support.handler.filler.JoinBasedResultFiller;
import com.geekhalo.lego.core.query.support.handler.filler.ListSmartResultFiller;
import com.geekhalo.lego.core.query.support.handler.filler.PageSmartResultFiller;
import com.geekhalo.lego.core.query.support.handler.filler.SmartResultFillers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfiguration {
    @Bean
    public JoinBasedResultFiller joinBasedResultFiller(){
        return new JoinBasedResultFiller();
    }

    @Bean
    public ListSmartResultFiller listSmartResultFiller(){
        return new ListSmartResultFiller();
    }

    @Bean
    public PageSmartResultFiller pageSmartResultFiller(){
        return new PageSmartResultFiller();
    }

    @Bean
    public SmartResultFillers smartResultFillers(){
        return new SmartResultFillers();
    }
}
