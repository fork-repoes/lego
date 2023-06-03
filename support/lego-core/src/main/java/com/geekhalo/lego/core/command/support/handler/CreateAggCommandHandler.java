package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.CommandForCreate;
import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.ContextForCreate;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.BiFunction;
import java.util.function.Function;

@Setter
public class CreateAggCommandHandler<
        CMD extends CommandForCreate,
        CONTEXT extends ContextForCreate<CMD>,
        AGG extends AggRoot,
        RESULT>
        extends AbstractAggCommandHandler<CMD, CONTEXT, AGG, RESULT>{

    private Function<CMD, CONTEXT> contextFactory;
    private Function<CONTEXT, AGG> aggFactory;
    private BiFunction<AGG, CONTEXT, RESULT> resultConverter;

    public CreateAggCommandHandler(ValidateService validateService,
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
        return this.aggFactory.apply(context);
    }

    @Override
    protected RESULT convertToResult(AGG agg, CONTEXT proxy) {
        return this.resultConverter.apply(agg, proxy);
    }

}
