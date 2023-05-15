package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.CommandRepository;
import com.google.common.base.Preconditions;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public abstract class AbstractCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandService.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 创建 Creator，已完成对创建流程的组装
     * @param repository
     * @param <ID>
     * @param <A>
     * @param <CONTEXT>
     * @return
     */
    protected <ID, A extends AggRoot<ID>, CONTEXT> Creator<ID, A, CONTEXT> creatorFor(CommandRepository<A, ID> repository){
        return new Creator<ID, A, CONTEXT>(repository);
    }

    /**
     * 创建 Updater，已完成对更新流程的组装
     * @param aggregateRepository
     * @param <ID>
     * @param <A>
     * @param <CONTEXT>
     * @return
     */
    protected <ID, A extends AggRoot<ID>, CONTEXT> Updater<ID, A, CONTEXT> updaterFor(CommandRepository<A, ID> aggregateRepository){
        return new Updater<ID, A, CONTEXT>(aggregateRepository);
    }

    /**
     * 创建 Syncer，已完成对同步流程的组装
     * @param aggregateRepository
     * @param <ID>
     * @param <A>
     * @param <CONTEXT>
     * @return
     */
    protected <ID, A extends AggRoot<ID>, CONTEXT> Syncer<ID, A, CONTEXT> syncerFor(CommandRepository<A, ID> aggregateRepository){
        return new Syncer<ID, A, CONTEXT>(aggregateRepository);
    }

    /**
     * 创建流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <A>
     * @param <CONTEXT>
     */
    protected class Creator<ID, A extends AggRoot<ID>, CONTEXT>{
        // 标准仓库
        private final CommandRepository<A, ID> aggregateRepository;

        // 实例化器，用于完成对 聚合 的实例化
        private Function<CONTEXT, A> instanceFun;

        // 默认的操作成功处理器，在操作成功后进行回调
        private BiConsumer<A, CONTEXT> successFun = (agg, context)->{
            LOGGER.info("success to handle {} and sync {} to DB", context, agg);
        };

        // 默认的异常处理器，在操作失败抛出异常时进行回调
        private BiConsumer<Exception, CONTEXT> errorFun = (exception, context) -> {
            LOGGER.error("failed to handle {}", context, exception);
            if (exception instanceof RuntimeException){
                throw (RuntimeException) exception;
            }
            throw new RuntimeException(exception);
        };

        // 聚合业务操作方法回调，最主要的扩展点，用于执行 聚合上的业务方法
        private BiConsumer<A, CONTEXT> updateFun = (a, context) -> {};


        Creator(CommandRepository<A, ID> aggregateRepository) {
            Preconditions.checkArgument(aggregateRepository != null);
            this.aggregateRepository = aggregateRepository;
        }

        /**
         * 设置 聚合的实例化器
         * @param instanceFun
         * @return
         */
        public Creator<ID, A, CONTEXT> instance(Function<CONTEXT, A> instanceFun){
            Preconditions.checkArgument(instanceFun != null);
            this.instanceFun = instanceFun;
            return this;
        }

        /**
         * 增加 聚合上的业务操作，链式模式，可以绑定多的 业务操作
         * @param updater
         * @return
         */
        public Creator<ID, A, CONTEXT> update(BiConsumer<A, CONTEXT> updater){
            Preconditions.checkArgument(updater != null);
            this.updateFun = this.updateFun.andThen(updater);
            return this;
        }

        /**
         * 增加 成功处理器，链式模式，可以绑定多个处理器
         * @param onSuccessFun
         * @return
         */
        public Creator<ID, A, CONTEXT> onSuccess(BiConsumer<A, CONTEXT>  onSuccessFun){
            Preconditions.checkArgument(onSuccessFun != null);
            this.successFun = onSuccessFun.andThen(this.successFun);
            return this;
        }

        /**
         * 增加 异常处理器，链式模式，可以绑定多个处理器
         * @param errorFun
         * @return
         */
        public Creator<ID, A, CONTEXT> onError(BiConsumer<Exception, CONTEXT>  errorFun){
            Preconditions.checkArgument(errorFun != null);
            this.errorFun = errorFun.andThen(this.errorFun);
            return this;
        }

        /**
         * 执行 创建流程
         * @param context
         * @return
         */
        public A call(CONTEXT context){
            Preconditions.checkArgument(this.instanceFun != null, "instance fun can not be null");
            Preconditions.checkArgument(this.aggregateRepository != null, "aggregateRepository can not be null");

            A a = null;
            try{
                // 实例化 聚合根
                a = this.instanceFun.apply(context);

                // 在聚合根上执行业务操作
                this.updateFun.accept(a, context);

                // 持久化聚合根到 DB
                this.aggregateRepository.sync(a);

                // 发布领域事件，并进行清理
                if (eventPublisher != null){
                    // 1. 发布领域事件
                    // 2. 清理领域事件
                    a.consumeAndClearEvent(eventPublisher::publishEvent);
                }

                // 调用 成功回调器
                this.successFun.accept(a, context);
            }catch (Exception e){
                // 调用 异常回调器
                this.errorFun.accept(e, context);
            }
            return a;
        }
    }

    /**
     * 更新流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <A>
     * @param <CONTEXT>
     */
    protected class Updater<ID, A extends AggRoot<ID>, CONTEXT> {
        // 标准仓库
        private final CommandRepository<A, ID> aggregateRepository;
        // 聚合加载器，用于从 DB 中加载 聚合对象
        private Function<CONTEXT, Optional<A>> loadFun;
        // 聚合丢失处理器，聚合丢失时进行回调
        private Consumer<CONTEXT> onNotExistFun = context -> {};
        // 成功回调器，链式模式，在操作成功时调用
        private BiConsumer<A, CONTEXT> successFun = (agg, context)->{
            LOGGER.info("success to handle {} and sync {} to DB", context, agg);
        };

        // 异常回调器，链式模式，在操作失败抛出异常时调用
        private BiConsumer<Exception, CONTEXT> errorFun = (exception, context) -> {
            LOGGER.error("failed to handle {}", context, exception);
            if (exception instanceof RuntimeException){
                throw (RuntimeException) exception;
            }
            throw new RuntimeException(exception);
        };
        // 业务更新器，对聚合进行业务操作
        private BiConsumer<A, CONTEXT> updateFun = (a, context) -> {};


        Updater(CommandRepository<A, ID> aggregateRepository) {
            this.aggregateRepository = aggregateRepository;
        }

        /**
         * 设置 聚合对象加载器，用于从 DB 中加载 聚合
         * @param loader
         * @return
         */
        public Updater<ID, A, CONTEXT> loadBy(Function<CONTEXT, Optional<A>> loader){
            Preconditions.checkArgument(loader != null);
            this.loadFun = loader;
            return this;
        }

        /**
         * 增加 业务执行器，链式模式，可以绑定多个执行器
         * @param updateFun
         * @return
         */
        public Updater<ID, A, CONTEXT> update(BiConsumer<A, CONTEXT> updateFun){
            Preconditions.checkArgument(updateFun != null);
            this.updateFun = updateFun.andThen(this.updateFun);
            return this;
        }

        /**
         * 增加 成功回调器，链式模式，可以绑定多个回调器
         * @param onSuccessFun
         * @return
         */
        public Updater<ID, A, CONTEXT> onSuccess(BiConsumer<A, CONTEXT>  onSuccessFun){
            Preconditions.checkArgument(onSuccessFun != null);
            this.successFun = onSuccessFun.andThen(this.successFun);
            return this;
        }

        /**
         * 增加 异常回调器，链式模式，可以绑定多个回调器
         * @param errorFun
         * @return
         */
        public Updater<ID, A, CONTEXT> onError(BiConsumer<Exception, CONTEXT>  errorFun){
            Preconditions.checkArgument(errorFun != null);
            this.errorFun = errorFun.andThen(this.errorFun);
            return this;
        }

        /**
         * 增加 聚合丢失处理器，链式模式，可以绑定多个回调器
         * @param onNotExistFun
         * @return
         */
        public Updater<ID, A, CONTEXT> onNotFound(Consumer<CONTEXT>  onNotExistFun){
            Preconditions.checkArgument(onNotExistFun != null);
            this.onNotExistFun = onNotExistFun.andThen(this.onNotExistFun);
            return this;
        }

        /**
         * 执行更新流程
         * @param context
         * @return
         */
        public A exe(CONTEXT context){
            Preconditions.checkArgument(this.aggregateRepository != null, "aggregateRepository can not be null");
            Preconditions.checkArgument(this.loadFun != null, "loader can not both be null");
            Optional<A> aOptional = null;
            try {
                // 从 DB 中加载 聚合根
                aOptional = this.loadFun.apply(context);

                if (!aOptional.isPresent()){
                    // 聚合根不存在，回调 聚合丢失处理器
                    this.onNotExistFun.accept(context);
                }else {
                    A a = aOptional.get();
                    // 在聚合之上，执行业务操作
                    updateFun.accept(a, context);

                    // 对聚合进行持久化处理
                    this.aggregateRepository.sync(a);

                    if(eventPublisher != null) {
                        // 发布并清理事件
                        // 1. 发布领域事件
                        // 2. 清理领域事件
                        a.consumeAndClearEvent(eventPublisher::publishEvent);
                    }

                    // 操作成功回调
                    this.successFun.accept(a, context);
                }

            }catch (Exception e){
                // 异常回调
                this.errorFun.accept(e, context);
            }
            return aOptional.get();
        }
    }

    protected class Syncer<ID, A extends AggRoot<ID>, CONTEXT> {
        // 仓库
        private final CommandRepository<A, ID> aggregateRepository;
        // 用于实例化聚合根
        private Function<CONTEXT, A> instanceFun;
        // 用于加载聚合根
        private Function<CONTEXT, Optional<A>> loadFun;
        // 执行通用业务逻辑
        private BiConsumer<A, CONTEXT> updater = (a, context) ->{
            if (a instanceof AbstractEntity){
                ((AbstractEntity) a).preUpdate();
            }
        };
        // 执行创建后业务逻辑
        private BiConsumer<A, CONTEXT> updaterWhenCreate = (a, context) ->{
            if (a instanceof AbstractEntity){
                ((AbstractEntity)a).prePersist();
            }
        };

//        private ValidationHandler validationHandler = new ExceptionBasedValidationHandler();
        // 成功后回调
        private Consumer<Data> successFun = a -> LOGGER.info("success to sync {}", a);
        private BiConsumer<Data, Exception> errorFun = (a, e) -> {
            LOGGER.error("failed to sync {}.", a, e);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        };

        Syncer(CommandRepository<A, ID> aggregateRepository) {
            this.aggregateRepository = aggregateRepository;
        }

        /**
         * 初始化器
         * @param instanceFun
         * @return
         */
        public Syncer<ID, A, CONTEXT> instanceBy(Function<CONTEXT, A> instanceFun){
            Preconditions.checkArgument(instanceFun != null);
            this.instanceFun = instanceFun;
            return this;
        }

        /**
         * 加载器
         * @param loadFun
         * @return
         */
        public Syncer<ID, A, CONTEXT> loadBy(Function<CONTEXT, Optional<A>> loadFun){
            Preconditions.checkArgument(loadFun != null);
            this.loadFun = loadFun;
            return this;
        }

        /**
         * 业务逻辑执行器
         * @param updater
         * @return
         */
        public Syncer<ID, A, CONTEXT> update(BiConsumer<A, CONTEXT> updater){
            Preconditions.checkArgument(updater != null);
            this.updater = updater.andThen(this.updater);
            return this;
        }

        /**
         * 创建后回调
         * @param updater
         * @return
         */
        public Syncer<ID, A, CONTEXT> updateWhenCreate(BiConsumer<A, CONTEXT> updater){
            Preconditions.checkArgument(updater != null);
            this.updaterWhenCreate = updater.andThen(this.updaterWhenCreate);
            return this;
        }

//        public Syncer<ID, A, CONTEXT> validate(ValidationHandler handler){
//            Preconditions.checkArgument(handler != null);
//            this.validationHandler = handler;
//            return this;
//        }

        /**
         * 成功后回调
         * @param onSuccessFun
         * @return
         */
        public Syncer<ID, A, CONTEXT> onSuccess(Consumer<Data> onSuccessFun){
            Preconditions.checkArgument(onSuccessFun != null);
            this.successFun = onSuccessFun.andThen(this.successFun);
            return this;
        }

        /**
         * 异常回调
         * @param errorFun
         * @return
         */
        public Syncer<ID, A, CONTEXT> onError(BiConsumer<Data, Exception> errorFun){
            Preconditions.checkArgument(errorFun != null);
            this.errorFun = errorFun.andThen(this.errorFun);
            return this;
        }


        /**
         * 执行逻辑
         * @param context
         * @return
         */
        public A exe(CONTEXT context){
            Preconditions.checkArgument(this.aggregateRepository != null, "aggregateRepository can not be null");
            Preconditions.checkArgument(this.loadFun != null, "load fun can not be nul");
            Preconditions.checkArgument(this.instanceFun != null, "instance fun can noe be null");
            A a = null;
            try {
                // 加载聚合根
                Optional<A> aOptional = this.loadFun.apply(context);

                // 存在，走更新流程
                if (aOptional.isPresent()){
                    a = aOptional.get();
                    // 执行业务逻辑
                    updater.accept(a, context);

//                    a.validate(validationHandler);
//                    if (validationHandler instanceof ValidationChecker){
//                        ((ValidationChecker)validationHandler).check();
//                    }
                    // 同步到 DB
                    this.aggregateRepository.sync(a);

                    if (eventPublisher != null) {
                        // 发布领域事件
                        // 1. 发布事件
                        // 2. 清理事件
                        a.consumeAndClearEvent(eventPublisher::publishEvent);
                    }

                    // 成功毁掉
                    this.successFun.accept(new Data(a.getId(), Action.CREATE, a));
                }else {
                    // 创建聚合根
                    a = this.instanceFun.apply(context);
                    // 执行创建逻辑
                    updaterWhenCreate.accept(a, context);
                    // 执行业务逻辑
                    updater.accept(a, context);

//                    a.validate(validationHandler);
//                    if (validationHandler instanceof ValidationChecker){
//                        ((ValidationChecker)validationHandler).check();
//                    }
                    // 保存聚合根
                    this.aggregateRepository.sync(a);

                    if (eventPublisher != null) {
                        // 发布领域事件
                        // 1. 发布事件
                        // 2. 清理事件
                        a.consumeAndClearEvent(eventPublisher::publishEvent);
                    }

                    // 成功回调
                    this.successFun.accept(new Data(a.getId(), Action.UPDATE, a));
                }
            }catch (Exception e){
                // 异常回调
                this.errorFun.accept(new Data(null, null, a), e);
            }
            return a;
        }

        @Value
        class Data{
            private final ID id;
            private final Action action;
            private final A a;
        }
    }
    enum Action{
        CREATE, UPDATE
    }
}
