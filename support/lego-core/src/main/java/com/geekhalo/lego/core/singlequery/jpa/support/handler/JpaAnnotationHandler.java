package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.core.SmartComponent;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface JpaAnnotationHandler<A extends Annotation> extends SmartComponent<Annotation> {

    /**
     * 是否支持 Annotion
     * @param annotation
     * @return
     */
    @Override
    boolean support(Annotation annotation);

    /**
     * 创建 Predicate
     * @param root
     * @param query
     * @param criteriaBuilder
     * @param annotation
     * @param value
     * @param <E>
     * @return
     */
    <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, A annotation, Object value);

    /**
     * 查找 Entity 中的属性，主要用于配置检测
     * @param entityCls
     * @param a
     * @param queryType
     * @param <E>
     * @return
     */
    <E> Field findEntityField(Class<E> entityCls, A a, Class queryType);
}
