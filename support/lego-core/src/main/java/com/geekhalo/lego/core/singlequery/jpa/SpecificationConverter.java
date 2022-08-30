package com.geekhalo.lego.core.singlequery.jpa;

import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.QueryConverter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SpecificationConverter<E>
        extends QueryConverter<Specification<E>> {
}
