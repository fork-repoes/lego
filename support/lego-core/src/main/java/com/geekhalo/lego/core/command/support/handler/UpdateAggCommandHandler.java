package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Setter
public class UpdateAggCommandHandler<
        AGG extends AggRoot,
        CMD extends Command,
        CONTEXT extends ContextForCommand<CMD>,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private Function<CMD, CONTEXT> contextFactory;
    private BiFunction<CommandRepository<?, AGG>, CMD, Optional<AGG>> aggLoader;
    private BiFunction<AGG, CONTEXT, RESULT> resultConverter;
    // 聚合丢失处理器，聚合丢失时进行回调
    private Consumer<CONTEXT> onNotExistFun = context ->{
        throw new AggNotFoundException(context.getCommand());
    };

    public UpdateAggCommandHandler(ValidateService validateService,
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
        Optional<AGG> aggOptional = aggLoader.apply(this.getCommandRepository(), command);
        if (aggOptional.isPresent()){
            return aggOptional.get();
        }else {
            this.onNotExistFun.accept(context);
            return null;
        }
    }


    @Override
    protected RESULT convertToResult(AGG agg, CONTEXT proxy) {
        return this.resultConverter.apply(agg, proxy);
    }

    public void setContextFactory(Function<CMD, CONTEXT> contextFactory) {
        Preconditions.checkArgument(contextFactory != null);
        this.contextFactory = contextFactory;
    }

    public void setAggLoader(BiFunction<CommandRepository<?, AGG>, CMD, Optional<AGG>> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    public void setResultConverter(BiFunction<AGG, CONTEXT, RESULT> resultConverter) {
        Preconditions.checkArgument(resultConverter != null);
        this.resultConverter = resultConverter;
    }

    public void addOnNotFound(Consumer<CONTEXT>  onNotExistFun){
        Preconditions.checkArgument(onNotExistFun != null);
        this.onNotExistFun = onNotExistFun.andThen(this.onNotExistFun);
    }

}
