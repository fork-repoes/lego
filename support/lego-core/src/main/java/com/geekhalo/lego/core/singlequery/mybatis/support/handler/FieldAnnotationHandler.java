package com.geekhalo.lego.core.singlequery.mybatis.support.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface FieldAnnotationHandler<A extends Annotation> {
    boolean support(A a);

    void addCriteria(Object criteria, A a, Object value) throws Exception;

    Method getCriteriaMethod(Class criteriaCls, A a, Class valueCls) throws Exception;
}
