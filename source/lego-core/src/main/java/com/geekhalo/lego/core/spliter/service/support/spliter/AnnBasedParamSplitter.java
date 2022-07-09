//package com.geekhalo.lego.core.spliter.service.support.spliter;
//
//import com.gaotu.blocks.split.ParamSplitService;
//import com.gaotu.blocks.split.SplitParam;
//import com.google.common.base.Preconditions;
//import org.apache.commons.lang3.reflect.ConstructorUtils;
//import org.apache.commons.lang3.reflect.FieldUtils;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class AnnBasedParamSplitter extends AbstractParamSplitter{
//    private final ParamSplitService paramSplitService;
//
//    public AnnBasedParamSplitter(ParamSplitService paramSplitService) {
//        this.paramSplitService = paramSplitService;
//    }
//
//    @Override
//    protected List<Object> doSplit(Object param, int maxSize) {
//        try {
//            Class<?> aClass = param.getClass();
//            List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(aClass, SplitParam.class);
//            Preconditions.checkState(fieldsListWithAnnotation.size() == 1);
//            Field splitField = fieldsListWithAnnotation.get(0);
//            Object splitValue = FieldUtils.readField(splitField, param, true);
//            List<Object> split = this.paramSplitService.split(splitValue, maxSize);
//            return split.stream()
//                    .map(value->{
//                        try {
//                            Object o = ConstructorUtils.invokeConstructor(aClass);
//                            FieldUtils.getAllFieldsList(aClass).forEach(field -> {
//                                try {
//                                    Object v = null;
//                                    if (field.equals(splitField)){
//                                        v = value;
//                                    }else {
//                                        v = FieldUtils.readField(field, param, true);
//                                    }
//                                    FieldUtils.writeField(field, o, v, true);
//                                }catch (Exception e){
//                                    throw new RuntimeException(e);
//                                }
//                            });
//                            return o;
//                        }catch (Exception e){
//                            throw new RuntimeException(e);
//                        }
//                    }).collect(Collectors.toList());
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public boolean doSupport(Class paramType) {
//        return FieldUtils.getAllFieldsList(paramType).stream()
//                .anyMatch(field -> field.getAnnotation(SplitParam.class) != null);
//    }
//}
