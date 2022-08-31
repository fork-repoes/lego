package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.annotation.singlequery.FieldIn;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by taoli on 2022/8/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaFieldInHandler
    extends AbstractJpaAnnotationHandler<FieldIn>{
    public JpaFieldInHandler() {
        super(FieldIn.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldIn fieldIn, Object value) {
        if (value instanceof Collection) {

            return criteriaBuilder.in(root.get(fieldIn.value())).value((Collection<?>) value);
        }
        return null;
    }
}
