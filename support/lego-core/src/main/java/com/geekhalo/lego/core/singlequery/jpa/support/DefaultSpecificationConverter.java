package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultSpecificationConverter<E> implements SpecificationConverter<E> {
    @Override
    public Specification<E> convertForQuery(Object query) {
        return null;
    }

    @Override
    public Specification<E> convertForCount(Object query) {
        return null;
    }

    @Override
    public Pageable findPageable(Object query) {
        return null;
    }

    @Override
    public void validate(Class cls) {

    }
}
