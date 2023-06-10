package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.support.handler.AggCommandHandlerFactories;
import com.geekhalo.lego.core.command.support.handler.CommandParser;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactories;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PUBLIC)
abstract class BaseCommandServiceMethodInvokerFactory {
    private final Class<? extends AggRoot> aggClass;
    @Autowired
    private CommandParser commandParser;
    @Autowired
    private SmartAggSyncers smartAggSyncers;
    @Autowired
    private AggCommandHandlerFactories commandHandlerFactory;

    @Setter(AccessLevel.PUBLIC)
    private CommandRepository commandRepository;

    protected BaseCommandServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass) {
        Preconditions.checkArgument(aggClass != null, "Agg Class Can not be null");
        this.aggClass = aggClass;
    }

    public void init(){
        commandParser.parseAgg(this.aggClass);
        this.smartAggSyncers.addAggSyncer(new SmartCommandRepositoryBasedAggSyncer(this.commandRepository, this.aggClass));
    }



    @Value
    protected class BizMethodContext{
        private final Class contextClass;
        private final Method method;
    }
}
