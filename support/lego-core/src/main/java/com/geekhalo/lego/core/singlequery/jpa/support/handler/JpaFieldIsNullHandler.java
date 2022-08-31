package com.geekhalo.lego.core.singlequery.jpa.support.handler;

import com.geekhalo.lego.annotation.singlequery.FieldIsNull;

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
public class JpaFieldIsNullHandler
    extends AbstractJpaAnnotationHandler<FieldIsNull>{
    public JpaFieldIsNullHandler() {
        super(FieldIsNull.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldIsNull fieldIsNull, Object value) {
        if (value instanceof Boolean){
            if ((Boolean) value){
                return criteriaBuilder.isNull(root.get(fieldNameOf(fieldIsNull)));
            }else {
                return criteriaBuilder.isNotNull(root.get(fieldNameOf(fieldIsNull)));
            }
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return true;
    }

    @Override
    protected String fieldNameOf(FieldIsNull fieldIsNull) {
        return fieldIsNull.value();
    }
}
