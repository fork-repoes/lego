package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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

    @Override
    public <E> Field findEntityField(Class<E> entityCls, A a, Class queryType) {
        String fieldName = fieldNameOf(a);
        Field field = FieldUtils.getField(entityCls, fieldName, true);
        if (field == null){
            return null;
        }
        if (matchField(field, queryType)){
            return field;
        }
        return null;
    }

    protected abstract boolean matchField(Field field, Class queryType);

    protected abstract String fieldNameOf(A a);

    protected <E> Expression createExpression(Root<E> root, String path){
        Expression<E> result = null;
        for (String fieldName : path.split("\\.")){
            result = root.get(fieldName);
        }
        return result;
    }
}
