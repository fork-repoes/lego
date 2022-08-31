package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.annotation.singlequery.FieldLessThanOrEqualTo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by taoli on 2022/8/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaFieldLessThanOrEqualToHandler
    extends AbstractJpaAnnotationHandler<FieldLessThanOrEqualTo>{
    public JpaFieldLessThanOrEqualToHandler() {
        super(FieldLessThanOrEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldLessThanOrEqualTo fieldLessThanOrEqualTo, Object value) {
        if (value instanceof Comparable){
            return criteriaBuilder.lessThanOrEqualTo(root.get(fieldLessThanOrEqualTo.value()), (Comparable) value);
        }
        return null;
    }
}
