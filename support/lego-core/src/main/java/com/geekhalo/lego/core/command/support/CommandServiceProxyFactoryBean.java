package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.support.handler.AggCommandHandlerFactories;
import com.geekhalo.lego.core.command.support.handler.CommandParser;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactories;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartAggSyncers;
import com.geekhalo.lego.core.command.support.handler.contextfactory.SmartContextFactories;
import com.geekhalo.lego.core.command.support.handler.converter.SmartResultConverters;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CommandServiceProxyFactoryBean<B>
        implements FactoryBean<B>{
    private final Class commandService;

    @Autowired
    private CommandServiceProxyFactory commandServiceProxyFactory;


    public CommandServiceProxyFactoryBean(Class commandService) {
        this.commandService = commandService;
    }

    @Override
    public B getObject() throws Exception {
        return this.commandServiceProxyFactory.createCommandService(this.commandService);
    }

    @Override
    public Class<?> getObjectType() {
        return this.commandService;
    }

}
