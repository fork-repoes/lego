//package com.geekhalo.lego.core.web;
//
//import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//
//import java.lang.reflect.Method;
//
///**
// * Created by taoli on 2022/10/6.
// * gitee : https://gitee.com/litao851025/lego
// * 编程就像玩 Lego
// */
//public class WebMethodHandlerMapping extends RequestMappingInfoHandlerMapping {
//
//    @Override
//    protected boolean isHandler(Class<?> beanType) {
//        return AnnotatedElementUtils.hasAnnotation(beanType, AutoRegisterWebController.class);
//    }
//
//    @Override
//    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
//        AutoRegisterWebController autoRegisterWebController =
//                AnnotatedElementUtils.findMergedAnnotation(handlerType, AutoRegisterWebController.class);
//        if (autoRegisterWebController == null){
//            return null;
//        }
//        String basePath = autoRegisterWebController.basePath();
//
//        return RequestMappingInfo.paths(basePath + "/" + method.getName())
//                .methods(RequestMethod.POST)
//                .build();
//    }
//}
