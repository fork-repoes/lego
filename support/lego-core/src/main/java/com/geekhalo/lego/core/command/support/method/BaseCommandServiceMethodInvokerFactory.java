package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.command.support.handler.AggCommandHandlerFactories;
import com.geekhalo.lego.core.command.support.handler.CommandParser;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactories;
import com.geekhalo.lego.core.command.support.handler.aggloader.IDBasedAggLoader;
import com.geekhalo.lego.core.command.support.handler.aggloader.KeyBasedAggLoader;
import com.geekhalo.lego.core.command.support.handler.aggloader.SmartAggLoaders;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartAggSyncer;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartAggSyncers;
import com.geekhalo.lego.core.command.support.handler.aggsyncer.SmartCommandRepositoryBasedAggSyncer;
import com.geekhalo.lego.core.command.support.handler.contextfactory.SmartContextFactories;
import com.geekhalo.lego.core.command.support.handler.converter.ResultConverter;
import com.geekhalo.lego.core.command.support.handler.converter.SmartResultConverters;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PUBLIC)
abstract class BaseCommandServiceMethodInvokerFactory {
    private static final Set<Class> REGISTERED_COMMAND_TYPE = Sets.newHashSet();

    private final Class<? extends AggRoot> aggClass;
    @Autowired
    private CommandParser commandParser;
    @Autowired
    private SmartAggSyncers smartAggSyncers;
    @Autowired
    private AggCommandHandlerFactories commandHandlerFactory;

    @Setter(AccessLevel.PUBLIC)
    private CommandRepository commandRepository;
    @Autowired
    private SmartAggLoaders smartAggLoaders;

    protected BaseCommandServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass) {
        Preconditions.checkArgument(aggClass != null, "Agg Class Can not be null");
        this.aggClass = aggClass;
    }

    public void init(){
        commandParser.parseAgg(this.aggClass);
        this.smartAggSyncers.addAggSyncer(new SmartCommandRepositoryBasedAggSyncer(this.commandRepository, this.aggClass));
    }

    protected void autoRegisterAggLoaders(Class commandType) {
        if (REGISTERED_COMMAND_TYPE.contains(commandType)){
            return;
        }
        REGISTERED_COMMAND_TYPE.add(commandType);

        if (CommandForSync.class.isAssignableFrom(commandType) && getCommandRepository() instanceof CommandWithKeyRepository){
            this.smartAggLoaders.addSmartAggLoader(KeyBasedAggLoader.apply(commandType, getAggClass(),
                    (CommandWithKeyRepository) getCommandRepository(), cmd -> ((CommandForSync) cmd).getKey()));
        }

        if (CommandForUpdateById.class.isAssignableFrom(commandType)){
            this.smartAggLoaders.addSmartAggLoader(IDBasedAggLoader.apply(commandType, getAggClass(),
                    getCommandRepository(), cmd -> ((CommandForUpdateById)cmd).getId()));
        }

        if (CommandForUpdateByKey.class.isAssignableFrom(commandType) && getCommandRepository() instanceof CommandWithKeyRepository){
            this.smartAggLoaders.addSmartAggLoader(KeyBasedAggLoader.apply(commandType, getAggClass(),
                    (CommandWithKeyRepository) getCommandRepository(), cmd -> ((CommandForUpdateByKey)cmd).getKey()));
        }
    }


    @Value
    protected class BizMethodContext{
        private final Class contextClass;
        private final Method method;
    }
}
