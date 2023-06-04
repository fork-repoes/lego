package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.Consumer;

@Setter
public class UpdateAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggLoader<CommandRepository<?, AGG>, CMD, AGG> aggLoader;

    // 聚合丢失处理器，聚合丢失时进行回调
    private Consumer<CONTEXT> onNotExistFun = context ->{
        if (context instanceof ContextForCommand) {
            throw new AggNotFoundException(((ContextForCommand)context).getCommand());
        }else {
            throw new AggNotFoundException(context);
        }
    };

    public UpdateAggCommandHandler(ValidateService validateService,
                                   LazyLoadProxyFactory lazyLoadProxyFactory,
                                   CommandRepository commandRepository,
                                   ApplicationEventPublisher eventPublisher,
                                   TransactionTemplate transactionTemplate) {
        super(validateService, lazyLoadProxyFactory, commandRepository, eventPublisher, transactionTemplate);
    }

    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        Optional<AGG> aggOptional = aggLoader.load(this.getCommandRepository(), cmd);
        if (aggOptional.isPresent()){
            return aggOptional.get();
        }else {
            this.onNotExistFun.accept(context);
            return null;
        }
    }

    public void setAggLoader(AggLoader<CommandRepository<?, AGG>, CMD, AGG> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    public void addOnNotFound(Consumer<CONTEXT>  onNotExistFun){
        Preconditions.checkArgument(onNotExistFun != null);
        this.onNotExistFun = onNotExistFun.andThen(this.onNotExistFun);
    }

}
