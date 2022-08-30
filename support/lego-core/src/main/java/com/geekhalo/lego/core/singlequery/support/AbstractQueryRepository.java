package com.geekhalo.lego.core.singlequery.support;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.QueryRepository;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class AbstractQueryRepository<E> implements QueryRepository<E> {

    protected abstract <Q> Pageable findPageable(Q query);

    @Override
    public <Q, R> Page<R> pageOf(Q query, Function<E, R> converter) {
        Pageable pageable = findPageable(query);

        if (pageable == null){
            throw new IllegalArgumentException("Pageable Lost");
        }

        Long totalElement = countOf(query);

        List<R> content =  listOf(query, converter);

        return new Page<>(content, pageable, totalElement);
    }


}
