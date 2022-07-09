//package com.geekhalo.lego.core.spliter.service.support.spring;
//
//import com.gaotu.blocks.split.*;
//import com.gaotu.blocks.split.support.DefaultSplitService;
//import com.gaotu.blocks.split.support.executor.ParallelMethodExecutor;
//import com.gaotu.blocks.split.support.executor.SerialMethodExecutor;
//import com.gaotu.blocks.split.support.spring.invoker.MultipleParamSplitInvoker;
//import com.gaotu.blocks.split.support.spring.invoker.SingleParamSplitInvoker;
//import com.gaotu.blocks.split.support.spring.invoker.SplitInvoker;
//import com.gaotu.blocks.split.support.spring.invoker.SplitInvokerRegistry;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.ApplicationContext;
//
//import java.util.concurrent.ExecutorService;
//
//public class SplitInvokerProcessor implements BeanPostProcessor {
//    private final SplitInvokerRegistry splitInvokerRegistry;
//    @Autowired
//    private ApplicationContext applicationContext;
//    @Autowired
//    private ParamSplitService paramSplitService;
//    @Autowired
//    private ResultMergeService resultMergeService;
//
//    public SplitInvokerProcessor(SplitInvokerRegistry splitInvokerRegistry) {
//        this.splitInvokerRegistry = splitInvokerRegistry;
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        process(bean);
//        return bean;
//    }
//
//    private void process(Object bean) {
//        Class<?> targetClass = AopUtils.getTargetClass(bean);
//        MethodUtils.getMethodsListWithAnnotation(targetClass, Split.class).forEach(method -> {
//            Split split = method.getAnnotation(Split.class);
//
//            SplitService splitService = buildSplitService(split);
//
//            int sizePrePartition = split.sizePrePartition();
//            SplitInvoker splitInvoker = null;
//            if (method.getParameterCount() == 1){
//                splitInvoker = new SingleParamSplitInvoker(splitService, method, sizePrePartition);
//            }else {
//                splitInvoker = new MultipleParamSplitInvoker(splitService, method, sizePrePartition);
//            }
//
//            this.splitInvokerRegistry.register(method, splitInvoker);
//        });
//    }
//
//    private DefaultSplitService buildSplitService(Split split) {
//        MethodExecutor methodExecutor = buildMethodExecutor(split);
//        return new DefaultSplitService(this.paramSplitService, methodExecutor, this.resultMergeService);
//    }
//
//    private MethodExecutor buildMethodExecutor(Split split) {
//        ExecutorType executorType = split.executorType();
//        MethodExecutor methodExecutor = null;
//        if (executorType == ExecutorType.SERIAL){
//            methodExecutor = new SerialMethodExecutor();
//        }
//
//        if (executorType == ExecutorType.PARALLEL){
//            String executorName = split.executorName();
//            int taskPreThread = split.taskPreThread();
//            ExecutorService executorService = this.applicationContext.getBean(executorName, ExecutorService.class);
//            methodExecutor = new ParallelMethodExecutor(executorService, taskPreThread);
//        }
//        return methodExecutor;
//    }
//
//}
