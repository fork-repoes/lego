package com.geekhalo.lego.starter.web;

import com.geekhalo.lego.core.command.CommandServicesRegistry;
import com.geekhalo.lego.core.web.command.CommandDispatcherController;
import com.geekhalo.lego.core.web.command.CommandMethodRegistry;
import com.geekhalo.lego.core.web.command.CommandServicesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandWebConfiguration {
    @Bean
    public CommandDispatcherController commandDispatcherController(){
        return new CommandDispatcherController();
    }

    @Bean
    public CommandMethodRegistry commandMethodRegistry(){
        return new CommandMethodRegistry();
    }

    @Bean
    public CommandServicesProvider commandServicesProvider(){
        return new CommandServicesProvider();
    }

    @ConditionalOnMissingBean
    @Bean
    public CommandServicesRegistry commandServicesRegistry(){
        return new CommandServicesRegistry();
    }
}
