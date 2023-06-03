package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Setter
public class SyncAggCommandHandler<
        KEY,
        CMD extends CommandForUpdate<KEY>,
        CONTEXT extends ContextForUpdate<KEY, CMD>,
        AGG extends AggRoot,
        RESULT>
        extends AbstractAggCommandHandler<CMD, CONTEXT, AGG, RESULT>{

    private Function<CONTEXT, AGG> aggFactory;
    private Function<CMD, CONTEXT> contextFactory;
    private BiFunction<CommandRepository<?, ?>, KEY, Optional<AGG>> aggLoader;
    private BiConsumer<AGG, CONTEXT> bizMethod;
    private BiFunction<AGG, CONTEXT, RESULT> resultConverter;

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
        CommandForUpdate command = context.getCommand();
        try {
            Optional<AGG> aggOptional = aggLoader.apply(this.getCommandRepository(), (KEY) command.key());
            if (aggOptional.isPresent()){
                return aggOptional.get();
            }else {
                return this.aggFactory.apply(context);
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
}
