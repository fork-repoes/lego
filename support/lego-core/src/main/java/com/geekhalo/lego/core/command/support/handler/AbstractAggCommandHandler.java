package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.Command;
import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.ContextForCommand;
import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.function.BiConsumer;

public abstract class AbstractAggCommandHandler<
        CMD extends Command,
        CONTEXT extends ContextForCommand<CMD>,
        AGG extends AggRoot,
        RESULT> implements AggCommandHandler<CMD, RESULT>{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAggCommandHandler.class);

    private final LazyLoadProxyFactory lazyLoadProxyFactory;
    private final ValidateService validateService;
    @Getter(AccessLevel.PROTECTED)
    private final CommandRepository commandRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    @Setter(AccessLevel.PRIVATE)
    private BiConsumer<AGG, CONTEXT> bizMethod = (a, context) -> {};


    // 默认的操作成功处理器，在操作成功后进行回调
    @Setter(AccessLevel.PRIVATE)
    private BiConsumer<AGG, CONTEXT> successFun = (agg, context)->{
        LOGGER.info("success to handle {} and sync {} to DB", context, agg);
    };

    // 默认的异常处理器，在操作失败抛出异常时进行回调
    @Setter(AccessLevel.PRIVATE)
    private BiConsumer<Exception, CONTEXT> errorFun = (exception, context) -> {
        LOGGER.error("failed to handle {}", context, exception);
        if (exception instanceof RuntimeException){
            throw (RuntimeException) exception;
        }
        throw new RuntimeException(exception);
    };


    protected AbstractAggCommandHandler(ValidateService validateService,
                                        LazyLoadProxyFactory lazyLoadProxyFactory,
                                        CommandRepository commandRepository,
                                        ApplicationEventPublisher eventPublisher,
                                        TransactionTemplate transactionTemplate) {
        Preconditions.checkArgument(validateService != null);
        Preconditions.checkArgument(lazyLoadProxyFactory != null);
        Preconditions.checkArgument(commandRepository != null);
        Preconditions.checkArgument(eventPublisher != null);
        Preconditions.checkArgument(transactionTemplate != null);

        this.validateService = validateService;
        this.lazyLoadProxyFactory = lazyLoadProxyFactory;
        this.commandRepository = commandRepository;
        this.eventPublisher = eventPublisher;
        this.transactionTemplate = transactionTemplate;
    }


    @Override
    public RESULT handle(CMD cmd) {
        CONTEXT contextToCache = null;
        try {
            // 1. 入参校验
            Preconditions.checkArgument(cmd != null);

            // 2. 参数验证，主要是格式验证
            validateForCommand(cmd);

            // 3. 将 Command 封装为 Context 对象
            CONTEXT context = createContext(cmd);
            contextToCache = context;

            // 4. 封装 Context 对象成 Proxy，使其具备高级特性
            //  a. 延迟加载能力
            //  b. Spring Bean 注入能力
            CONTEXT contextProxy = createProxy(context);
            contextToCache = contextProxy;

            // 5. 执行业务验证
            validateForContext(contextProxy);

            // 6. 创建或加载聚合根对象
            AGG agg = getOrCreateAgg(contextProxy);

            // 7. 执行聚合根业务方法
            callBizMethod(agg, contextProxy);

            // 8. 执行通用规则校验
            validateAfterBizMethod(agg, contextProxy);

            this.transactionTemplate.execute(status -> {
                // 9. 发布领域事件
                publishEvent(agg, contextProxy);

                // 10. 保存至数据库
                syncToRepository(agg, contextProxy);
                return null;

            });

            RESULT result = convertToResult(agg, contextProxy);

            this.successFun.accept(agg, contextProxy);
            return result;
        }catch (Exception e){
            this.errorFun.accept(e, contextToCache);
        }
        return null;
    }

    protected void validateAfterBizMethod(AGG agg, CONTEXT context){
        this.validateService.validateRule(agg);
    }


    protected void validateForCommand(CMD cmd) {
        this.validateService.validateParam(Collections.singletonList(cmd));
    }

    protected abstract CONTEXT createContext(CMD cmd);

    protected CONTEXT createProxy(CONTEXT context){
        return this.lazyLoadProxyFactory.createProxyFor(context);
    }

    protected void validateForContext(CONTEXT context) {
        this.validateService.validateBusiness(context);
    }

    protected abstract AGG getOrCreateAgg(CONTEXT context);

    private void callBizMethod(AGG agg, CONTEXT proxy) {
        this.bizMethod.accept(agg, proxy);
    }

    protected void syncToRepository(AGG agg, CONTEXT context) {
        this.commandRepository.sync(agg);
    }

    protected void publishEvent(AGG agg, CONTEXT context) {
        agg.consumeAndClearEvent(event -> this.eventPublisher.publishEvent(event));
    }

    protected abstract RESULT convertToResult(AGG agg, CONTEXT proxy);

    public void addBizMethod(BiConsumer<AGG, CONTEXT> bizMethod){
        this.bizMethod = this.bizMethod.andThen(bizMethod);
    }

    /**
     * 增加 成功处理器，链式模式，可以绑定多个处理器
     * @param onSuccessFun
     * @return
     */
    public void addOnSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
        Preconditions.checkArgument(onSuccessFun != null);
        this.successFun = onSuccessFun.andThen(this.successFun);
    }

    /**
     * 增加 异常处理器，链式模式，可以绑定多个处理器
     * @param errorFun
     * @return
     */
    public void addOnError(BiConsumer<Exception, CONTEXT>  errorFun){
        Preconditions.checkArgument(errorFun != null);
        this.errorFun = errorFun.andThen(this.errorFun);
    }
}
