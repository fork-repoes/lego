package com.geekhalo.lego.core.web.support;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer;
import org.springframework.beans.BeanUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.KotlinReflectionParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by taoli on 2022/10/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class MultiParamMethod {
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER;
    static {
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        PARAMETER_NAME_DISCOVERER =  defaultParameterNameDiscoverer;
        defaultParameterNameDiscoverer.addDiscoverer(new KotlinReflectionParameterNameDiscoverer());
    }
    private final Object bean;
    private final Method method;
    @Getter
    private final Class[] paramTypes;
    @Getter
    private final String[] paramNames;

    @Getter
    private final NamedMethodParameter[] namedMethodParameters;

    @Getter
    private final HandlerMethod handlerMethod;

    public MultiParamMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;
        this.paramTypes = method.getParameterTypes();
        String[] pNames = PARAMETER_NAME_DISCOVERER.getParameterNames(this.method);
        if (pNames == null){
            this.paramNames = new String[this.paramTypes.length];
            for (int i = 0; i< this.paramNames.length; i++){
                this.paramNames[i] = "arg" + i;
            }
        }else {
            this.paramNames = pNames;
        }
        this.namedMethodParameters = buildParameters();
        this.handlerMethod = new HandlerMethod(bean, method);
    }

    private NamedMethodParameter[] buildParameters() {
        NamedMethodParameter[] result = new NamedMethodParameter[getParamTypes().length];
        for (int i = 0; i < getParamNames().length; i++){
            String paramName = getParamNames()[i];
            Class paramCls = getParamTypes()[i];
            Set<Class<? extends Annotation>> annotations = Sets.newHashSet();
            if (BeanUtils.isSimpleValueType(paramCls)){
                annotations.add(RequestParam.class);
            }else {
                annotations.add(ModelAttribute.class);
            }
            NamedMethodParameter methodParameter = new NamedMethodParameter(getMethod(), i, paramName, annotations);
            result[i] = methodParameter;
        }
        return result;
    }

    public Object invoke(Object[] params) throws Exception{
        return MethodUtils.invokeMethod(this.bean, this.method.getName(), params);
    }
}
