package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import java.lang.annotation.Annotation;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractJpaAnnotationHandler<A extends Annotation> implements JpaAnnotationHandler<A>{
    private final Class<A> annCls;

    public AbstractJpaAnnotationHandler(Class<A> annCls) {
        this.annCls = annCls;
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation != null && annotation.annotationType() == this.annCls;
    }

}
