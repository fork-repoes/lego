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
import java.util.function.BiFunction;
import java.util.function.Function;

public class SyncAggCommandHandler<
        AGG extends AggRoot,
        CMD extends Command,
        CONTEXT extends ContextForCommand<CMD>,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private Function<CONTEXT, AGG> aggFactory;
    private Function<CMD, CONTEXT> contextFactory;
    private BiFunction<CommandRepository<?, ?>, CMD, Optional<AGG>> aggLoader;
    private BiFunction<AGG, CONTEXT, RESULT> resultConverter;

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
    protected CONTEXT createContext(CMD param) {
        return this.contextFactory.apply(param);
    }

    @Override
    protected AGG getOrCreateAgg(CONTEXT context) {
        CMD command = context.getCommand();
        try {
            Optional<AGG> aggOptional = aggLoader.apply(this.getCommandRepository(), command);
            if (aggOptional.isPresent()){
                return aggOptional.get();
            }else {
                AGG agg = this.aggFactory.apply(context);

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

    @Override
    protected RESULT convertToResult(AGG agg, CONTEXT proxy) {
        return this.resultConverter.apply(agg, proxy);
    }


    public void addWhenCreate(BiConsumer<AGG, CONTEXT> updater){
        Preconditions.checkArgument(updater != null);
        this.updaterWhenCreate = updater.andThen(this.updaterWhenCreate);
    }

    public void setAggFactory(Function<CONTEXT, AGG> aggFactory) {
        Preconditions.checkArgument(aggFactory != null);
        this.aggFactory = aggFactory;
    }

    public void setContextFactory(Function<CMD, CONTEXT> contextFactory) {
        Preconditions.checkArgument(contextFactory != null);
        this.contextFactory = contextFactory;
    }

    public void setAggLoader(BiFunction<CommandRepository<?, ?>, CMD, Optional<AGG>> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    public void setResultConverter(BiFunction<AGG, CONTEXT, RESULT> resultConverter) {
        Preconditions.checkArgument(resultConverter != null);
        this.resultConverter = resultConverter;
    }

}
