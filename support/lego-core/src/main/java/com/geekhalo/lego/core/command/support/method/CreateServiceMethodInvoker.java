//package com.geekhalo.lego.core.command.support.method;
//
//import com.geekhalo.lego.core.command.AggRoot;
//import com.geekhalo.lego.core.command.CommandForCreate;
//import com.geekhalo.lego.core.command.CommandRepository;
//import com.geekhalo.lego.core.command.ContextForCreate;
//import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
//import com.geekhalo.lego.core.validator.ValidateService;
//import lombok.Setter;
//import org.springframework.context.ApplicationEventPublisher;
//
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
///**
// * Created by taoli on 2022/10/3.
// * gitee : https://gitee.com/litao851025/lego
// * 编程就像玩 Lego
// */
//@Setter
//public class CreateServiceMethodInvoker<C extends CommandForCreate,
//        CONTEXT extends ContextForCreate<C>,
//        AGG extends AggRoot,
//        RESULT>
//        extends BaseCommandServiceMethodInvoker<C, CONTEXT, AGG, RESULT>{
//    private Function<C, CONTEXT> contextFactory;
//    private Function<CONTEXT, AGG> aggFactory;
//    private BiConsumer<AGG, CONTEXT> bizMethod;
//    private BiFunction<AGG, CONTEXT, RESULT> resultConverter;
//
//    protected CreateServiceMethodInvoker(LazyLoadProxyFactory lazyLoadProxyFactory,
//                                         ValidateService validateService,
//                                         CommandRepository commandRepository,
//                                         ApplicationEventPublisher eventPublisher) {
//        super(lazyLoadProxyFactory, validateService, commandRepository, eventPublisher);
//    }
//
//    @Override
//    protected CONTEXT createContext(C param) {
//        return this.contextFactory.apply(param);
//    }
//
//    @Override
//    protected AGG getOrCreateAgg(CONTEXT proxy) {
//        return this.aggFactory.apply(proxy);
//    }
//
//    @Override
//    protected void callBizMethod(AGG agg, CONTEXT proxy) {
//        this.bizMethod.accept(agg, proxy);
//    }
//
//    @Override
//    protected RESULT convertToResult(AGG agg, CONTEXT proxy) {
//        return this.resultConverter.apply(agg, proxy);
//    }
//}
