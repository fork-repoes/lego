package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactories;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactory;
import com.geekhalo.lego.core.command.support.handler.aggloader.SmartAggLoader;
import com.geekhalo.lego.core.command.support.handler.aggloader.SmartAggLoaders;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartAggSyncer;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartAggSyncers;
import com.geekhalo.lego.core.command.support.handler.contextfactory.ContextFactory;
import com.geekhalo.lego.core.command.support.handler.contextfactory.SmartContextFactories;
import com.geekhalo.lego.core.command.support.handler.converter.ResultConverter;
import com.geekhalo.lego.core.command.support.handler.converter.SmartResultConverters;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Setter
@Component
public class AggCommandHandlerFactories{
    @Autowired
    private ValidateService validateService;
    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SmartResultConverters smartResultConverters;
    @Autowired
    private SmartContextFactories smartContextFactories;
    @Autowired
    private SmartAggSyncers smartAggSyncers;
    @Autowired
    private SmartAggFactories smartAggFactories;
    @Autowired
    private SmartAggLoaders smartAggLoaders;


    public <CMD, RESULT> CommandHandler<CMD, RESULT> createCreateAggCommandHandler(Class aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggFactory aggFactory = this.smartAggFactories.findAggFactoryOrNull(contextClass, aggClass);
        if (aggFactory == null){
            return null;
        }
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }
        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }
        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        CreateAggCommandHandler commandHandler = new CreateAggCommandHandler(this.validateService, this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate);
        commandHandler.setAggFactory(aggFactory);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);

        commandHandler.validate();
        return commandHandler;
    }

    public UpdateAggCommandHandler createUpdateAggCommandHandler(Class aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }

        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }

        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        SmartAggLoader aggLoader = this.smartAggLoaders.findAggLoaderOrNull(cmdClass, aggClass);
        if (aggLoader == null){
            return null;
        }

        UpdateAggCommandHandler commandHandler = new UpdateAggCommandHandler(this.validateService, this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);
        commandHandler.setAggLoader(aggLoader);

        commandHandler.validate();
        return commandHandler;
    }

    public SyncAggCommandHandler createSyncAggCommandHandler(Class<? extends AggRoot> aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }

        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }

        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        SmartAggLoader aggLoader = this.smartAggLoaders.findAggLoaderOrNull(cmdClass, aggClass);
        if (aggLoader == null){
            return null;
        }

        SmartAggFactory aggFactory = this.smartAggFactories.findAggFactoryOrNull(contextClass, aggClass);
        if (aggFactory == null){
            return null;
        }

        SyncAggCommandHandler commandHandler = new SyncAggCommandHandler(this.validateService, this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);
        commandHandler.setAggLoader(aggLoader);
        commandHandler.setAggFactory(aggFactory);

        commandHandler.validate();
        return commandHandler;
    }
}
