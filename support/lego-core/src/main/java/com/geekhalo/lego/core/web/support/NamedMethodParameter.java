package com.geekhalo.lego.core.web.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class NamedMethodParameter extends MethodParameter {
        private final String name;
        private final Set<Class<? extends Annotation>> annotations;
        public NamedMethodParameter(Method method, int parameterIndex,
                                    String name, Set<Class<? extends Annotation>> annotations) {
            super(method, parameterIndex);
            this.name = name;
            this.annotations = annotations;
        }

        @Override
        public String getParameterName(){
            if (StringUtils.isNotEmpty(name)){
                return name;
            }
            return "";
        }

        @Override
        public <A extends Annotation> boolean hasParameterAnnotation(Class<A> annotationType) {
            return this.annotations.contains(annotationType);
        }
    }