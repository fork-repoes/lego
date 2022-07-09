//package com.geekhalo.lego.core.spliter.service.support.spring.invoker;
//
//import com.gaotu.blocks.split.SplitParam;
//import com.gaotu.blocks.split.SplitService;
//import com.gaotu.blocks.split.support.spliter.InvokeParams;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//
//public class MultipleParamSplitInvoker
//        extends AbstractSplitInvoker
//        implements SplitInvoker {
//
//    public MultipleParamSplitInvoker(SplitService splitService, Method method, int sizePrePartition) {
//        super(splitService, method, sizePrePartition);
//    }
//
//    @Override
//    public Object invoke(Object target, Object[] params) {
//        int splitParamIndex = 0;
//        Parameter[] parameters = getMethod().getParameters();
//        for (int i = 0;i < parameters.length; i ++){
//            Parameter parameter = parameters[i];
//            SplitParam annotation = parameter.getAnnotation(SplitParam.class);
//            if (annotation != null){
//                splitParamIndex = i;
//            }
//
//        }
//        InvokeParams invokeParams = InvokeParams.builder()
//                .splitParamIndex(splitParamIndex)
//                .parameters(params)
//                .build();
//        return this.getSplitService().split(param->{
//            try {
//                return getMethod().invoke(target, param.getParameters());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }, invokeParams, getSizePrePartition());
//    }
//}
