//package com.geekhalo.lego.core.spliter.service.support.spring.invoker;
//
//import com.gaotu.blocks.split.SplitService;
//
//import java.lang.reflect.Method;
//
//public class SingleParamSplitInvoker
//        extends AbstractSplitInvoker
//        implements SplitInvoker {
//
//
//    public SingleParamSplitInvoker(SplitService splitService, Method method, int sizePrePartition) {
//        super(splitService, method, sizePrePartition);
//    }
//
//    @Override
//    public Object invoke(Object target, Object[] args) {
//        return this.getSplitService().split(param->{
//            Object[] paramToUse = new Object[]{param};
//            try {
//                return getMethod().invoke(target, paramToUse);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        },args[0], getSizePrePartition());
//    }
//}
