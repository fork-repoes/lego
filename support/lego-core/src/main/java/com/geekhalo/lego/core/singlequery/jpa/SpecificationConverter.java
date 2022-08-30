package com.geekhalo.lego.core.singlequery.jpa;

import com.geekhalo.lego.core.singlequery.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SpecificationConverter<E> {
    Specification<E> convertForQuery(Object query);

    Specification<E> convertForCount(Object query);

    Pageable findPageable(Object query);

    void validate(Class cls);
}
