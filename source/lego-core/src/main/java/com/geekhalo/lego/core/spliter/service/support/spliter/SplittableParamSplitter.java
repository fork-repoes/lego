//package com.geekhalo.lego.core.spliter.service.support.spliter;
//
//import com.gaotu.blocks.common.split.SplittableParam;
//import com.gaotu.blocks.split.ParamSplitter;
//
//import java.util.List;
//
//public class SplittableParamSplitter<P extends SplittableParam<P>>
//        extends AbstractParamSplitter<P>
//        implements ParamSplitter<P> {
//
//    @Override
//    public boolean doSupport(Class<P> paramType) {
//        return SplittableParam.class.isAssignableFrom(paramType);
//    }
//
//
//    @Override
//    protected List<P> doSplit(P param, int maxSize) {
//        return param.split(maxSize);
//    }
//}
