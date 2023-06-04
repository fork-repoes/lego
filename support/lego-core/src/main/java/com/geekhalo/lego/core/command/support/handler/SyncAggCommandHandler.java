package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.command.support.AbstractEntity;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.BiConsumer;

public class SyncAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggFactory<CONTEXT, AGG> aggFactory;

    private AggLoader<CommandRepository<?, ?>, CMD, AGG> aggLoader;

    private BiConsumer<AGG, CONTEXT> updaterWhenCreate = (a, context) ->{
        if (a instanceof AbstractEntity){
            ((AbstractEntity)a).prePersist();
        }
    };

    public SyncAggCommandHandler(ValidateService validateService,
                                 LazyLoadProxyFactory lazyLoadProxyFactory,
                                 CommandRepository commandRepository,
                                 ApplicationEventPublisher eventPublisher,
                                 TransactionTemplate transactionTemplate) {
        super(validateService, lazyLoadProxyFactory, commandRepository, eventPublisher, transactionTemplate);
    }

    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        try {
            Optional<AGG> aggOptional = aggLoader.load(this.getCommandRepository(), cmd);
            if (aggOptional.isPresent()){
                return aggOptional.get();
            }else {
                AGG agg = this.aggFactory.create(context);

                updaterWhenCreate.accept(agg, context);

                return agg;
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }


    public void addWhenCreate(BiConsumer<AGG, CONTEXT> updater){
        Preconditions.checkArgument(updater != null);
        this.updaterWhenCreate = updater.andThen(this.updaterWhenCreate);
    }

    public void setAggFactory(AggFactory<CONTEXT, AGG> aggFactory) {
        Preconditions.checkArgument(aggFactory != null);
        this.aggFactory = aggFactory;
    }



    public void setAggLoader(AggLoader<CommandRepository<?, ?>, CMD, AGG> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

}
