package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.annotation.singlequery.FieldNotEqualTo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaFieldNotEqualToHandler
    extends AbstractJpaAnnotationHandler<FieldNotEqualTo>{
    public JpaFieldNotEqualToHandler() {
        super(FieldNotEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldNotEqualTo fieldNotEqualTo, Object value) {
        return criteriaBuilder.notEqual(root.get(fieldNameOf(fieldNotEqualTo)), value);
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return field.getType() == queryType;
    }

    @Override
    protected String fieldNameOf(FieldNotEqualTo fieldNotEqualTo) {
        return fieldNotEqualTo.value();
    }
}
