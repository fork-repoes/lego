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

    <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, A annotation, Object value);

    <E> Field findEntityField(Class<E> entityCls, A a, Class queryType);
}
