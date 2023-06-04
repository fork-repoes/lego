package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.command.support.handler.CreateAggCommandHandler;
import com.geekhalo.lego.core.command.support.handler.SyncAggCommandHandler;
import com.geekhalo.lego.core.command.support.handler.UpdateAggCommandHandler;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public abstract class AbstractCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandService.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 创建 Creator，已完成对创建流程的组装
     * @param repository
     * @param <ID>
     * @param <A>
     * @param <CMD>>
     * @return
     */
    protected <ID, A extends AggRoot<ID>, CMD extends Command> Creator<ID, A, CMD> creatorFor(CommandRepository<A, ID> repository){
        return new Creator<ID, A, CMD>(repository);
    }

    /**
     * 创建 Updater，已完成对更新流程的组装
     * @param aggregateRepository
     * @param <ID>
     * @param <A>
     * @param <CONTEXT>
     * @return
     */
    protected <AGG extends AggRoot<?>,
            CMD extends Command,
            CONTEXT extends ContextForCommand<CMD>>
        Updater<AGG, CMD, CONTEXT> updaterFor(CommandRepository<AGG, ?> aggregateRepository){
        return new Updater<AGG, CMD, CONTEXT>(aggregateRepository);
    }

    /**
     * 创建 Syncer，已完成对同步流程的组装
     * @param aggregateRepository
     * @param <ID>
     * @param <A>
     * @param <CMD>
     * @return
     */
    protected <CMD extends Command,
            CONTEXT extends ContextForCommand<CMD>,
            AGG extends AggRoot<?>>
        Syncer<CMD, CONTEXT, AGG> syncerFor(CommandRepository<AGG, ?> aggregateRepository){
        return new Syncer<CMD, CONTEXT, AGG> (aggregateRepository);
    }

    /**
     * 创建流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <A>
     * @param <CMD>
     */
    protected class Creator<ID, A extends AggRoot<ID>, CMD extends Command>{
        // 标准仓库
        private final CreateAggCommandHandler commandHandler;


        Creator(CommandRepository<A, ID> aggregateRepository) {
            Preconditions.checkArgument(aggregateRepository != null);
            this.commandHandler = new CreateAggCommandHandler(validateService, lazyLoadProxyFactory, aggregateRepository, eventPublisher, transactionTemplate);
            this.commandHandler.setContextFactory(cmd -> cmd);
            this.commandHandler.setResultConverter((agg, context) -> context);
        }

        /**
         * 设置 聚合的实例化器
         * @param instanceFun
         * @return
         */
        public Creator<ID, A, CMD> instance(Function<CMD, A> instanceFun){
            Preconditions.checkArgument(instanceFun != null);
            this.commandHandler.setAggFactory(instanceFun);
            return this;
        }

        /**
         * 增加 聚合上的业务操作，链式模式，可以绑定多的 业务操作
         * @param updater
         * @return
         */
        public Creator<ID, A, CMD> update(BiConsumer<A, CMD> updater){
            Preconditions.checkArgument(updater != null);
            this.commandHandler.addBizMethod(updater);
            return this;
        }

        /**
         * 增加 成功处理器，链式模式，可以绑定多个处理器
         * @param onSuccessFun
         * @return
         */
        public Creator<ID, A, CMD> onSuccess(BiConsumer<A, CMD>  onSuccessFun){
            Preconditions.checkArgument(onSuccessFun != null);
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 增加 异常处理器，链式模式，可以绑定多个处理器
         * @param errorFun
         * @return
         */
        public Creator<ID, A, CMD> onError(BiConsumer<Exception, CMD>  errorFun){
            Preconditions.checkArgument(errorFun != null);
            this.commandHandler.addOnError(errorFun);
            return this;
        }

        /**
         * 执行 创建流程
         * @param cmd
         * @return
         */
        public A call(CMD cmd){
            return (A) commandHandler.handle(cmd);
        }
    }

    /**
     * 更新流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <A>
     * @param <CONTEXT>
     */
    protected class Updater<AGG extends AggRoot<?>,
            CMD extends Command,
            CONTEXT extends ContextForCommand<CMD>> {
        private final UpdateAggCommandHandler<AGG, CMD, CONTEXT, AGG> commandHandler;

        Updater(CommandRepository<AGG, ?> aggregateRepository) {
            this.commandHandler = new UpdateAggCommandHandler<>(validateService, lazyLoadProxyFactory, aggregateRepository, eventPublisher, transactionTemplate);
            this.commandHandler.setResultConverter((agg, context) -> agg);
        }

        public Updater<AGG, CMD, CONTEXT> contextFactory(Function<CMD, CONTEXT> contextFactory){
            this.commandHandler.setContextFactory(contextFactory);
            return this;
        }

        /**
         * 设置 聚合对象加载器，用于从 DB 中加载 聚合
         * @param loader
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> loadBy(Function<CMD, Optional<AGG>> loader){
            this.commandHandler.setAggLoader(((aggCommandRepository, cmd) -> loader.apply(cmd)));
            return this;
        }

        /**
         * 增加 业务执行器，链式模式，可以绑定多个执行器
         * @param updateFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> update(BiConsumer<AGG, CONTEXT> updateFun){
            this.commandHandler.addBizMethod(updateFun);
            return this;
        }

        /**
         * 增加 成功回调器，链式模式，可以绑定多个回调器
         * @param onSuccessFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 增加 异常回调器，链式模式，可以绑定多个回调器
         * @param errorFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onError(BiConsumer<Exception, CONTEXT>  errorFun){
            this.commandHandler.addOnError(errorFun);
            return this;
        }

        /**
         * 增加 聚合丢失处理器，链式模式，可以绑定多个回调器
         * @param onNotExistFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onNotFound(Consumer<CONTEXT>  onNotExistFun){
            this.commandHandler.addOnNotFound(onNotExistFun);
            return this;
        }

        /**
         * 执行更新流程
         * @param context
         * @return
         */
        public AGG exe(CMD cmd){
            return commandHandler.handle(cmd);

        }
    }

    protected class Syncer<
            CMD extends Command,
            CONTEXT extends ContextForCommand<CMD>,
            AGG extends AggRoot<?>> {
        private final SyncAggCommandHandler<AGG, CMD, CONTEXT, AGG> commandHandler;

        Syncer(CommandRepository<AGG, ?> aggregateRepository) {
            this.commandHandler = new SyncAggCommandHandler(validateService, lazyLoadProxyFactory, aggregateRepository, eventPublisher, transactionTemplate);
            this.commandHandler.setResultConverter((agg, context) -> agg);
        }

        public Syncer< CMD, CONTEXT, AGG> contextFactory(Function<CMD, CONTEXT> contextFactory){
            this.commandHandler.setContextFactory(contextFactory);
            return this;
        }

        /**
         * 初始化器
         * @param instanceFun
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> instanceBy(Function<CONTEXT, AGG> instanceFun){
            this.commandHandler.setAggFactory(instanceFun);
            return this;
        }

        /**
         * 加载器
         * @param loadFun
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> loadBy(Function<CMD, Optional<AGG>> loadFun){
            this.commandHandler.setAggLoader((repository, cmd) -> loadFun.apply(cmd));
            return this;
        }

        /**
         * 业务逻辑执行器
         * @param updater
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> update(BiConsumer<AGG, CONTEXT> updater){
            this.commandHandler.addBizMethod(updater);
            return this;
        }

        /**
         * 创建后回调
         * @param updater
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> updateWhenCreate(BiConsumer<AGG, CONTEXT> updater){
            this.commandHandler.addWhenCreate(updater);
            return this;
        }


        /**
         * 成功后回调
         * @param onSuccessFun
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> onSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 异常回调
         * @param errorFun
         * @return
         */
        public Syncer< CMD, CONTEXT, AGG> onError(BiConsumer<Exception, CONTEXT> errorFun){
            this.commandHandler.addOnError(errorFun);
            return this;
        }


        /**
         * 执行逻辑
         * @param cmd
         * @return
         */
        public AGG exe(CMD cmd) {
            return this.commandHandler.handle(cmd);
        }

    }

}
