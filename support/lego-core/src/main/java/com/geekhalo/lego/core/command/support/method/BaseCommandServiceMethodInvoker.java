package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.Command;
import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.ContextForCommand;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class BaseCommandServiceMethodInvoker<
        C extends Command,
        CONTEXT extends ContextForCommand<C>,
        AGG extends AggRoot,
        RESULT> implements ServiceMethodInvoker {
    private final LazyLoadProxyFactory lazyLoadProxyFactory;
    private final ValidateService validateService;
    @Getter(AccessLevel.PROTECTED)
    private final CommandRepository commandRepository;
    private final ApplicationEventPublisher eventPublisher;

    protected BaseCommandServiceMethodInvoker(LazyLoadProxyFactory lazyLoadProxyFactory,
                                              ValidateService validateService,
                                              CommandRepository commandRepository,
                                              ApplicationEventPublisher eventPublisher) {
        this.lazyLoadProxyFactory = lazyLoadProxyFactory;
        this.validateService = validateService;
        this.commandRepository = commandRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Object invoke(Method method, Object[] arguments) {
        Preconditions.checkArgument(arguments.length == 1);
        Object param = arguments[0];

        CONTEXT context = createContext((C) param);
        CONTEXT proxy = createProxy(context);

        validateForContext(proxy);

        AGG agg = getOrCreateAgg(proxy);

        callBizMethod(agg, proxy);

        syncToRepository(agg, proxy);

        publishEvent(agg, proxy);

        RESULT result = convertToResult(agg, proxy);
        return result;
    }

    protected abstract CONTEXT createContext(C param);

    protected CONTEXT createProxy(CONTEXT context){
        return this.lazyLoadProxyFactory.createProxyFor(context);
    }

    protected void validateForContext(CONTEXT proxy) {
        this.validateService.validate(proxy);
    }

    protected abstract AGG getOrCreateAgg(CONTEXT proxy);

    protected abstract void callBizMethod(AGG agg, CONTEXT proxy);

    protected void syncToRepository(AGG agg, CONTEXT proxy) {
        this.commandRepository.sync(agg);
    }

    protected void publishEvent(AGG agg, CONTEXT proxy) {
        agg.consumeAndClearEvent(event -> this.eventPublisher.publishEvent(event));
    }

    protected abstract RESULT convertToResult(AGG agg, CONTEXT proxy);
}
