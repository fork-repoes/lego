package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaFieldEqualToHandler
        extends AbstractJpaAnnotationHandler<FieldEqualTo>
        implements JpaAnnotationHandler<FieldEqualTo>{

    public JpaFieldEqualToHandler() {
        super(FieldEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldEqualTo fieldEqualTo, Object value) {
        return criteriaBuilder.equal(root.get(fieldEqualTo.value()), value);
    }
}
