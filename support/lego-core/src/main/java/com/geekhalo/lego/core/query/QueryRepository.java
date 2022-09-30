package com.geekhalo.lego.core.query;

import com.geekhalo.lego.core.singlequery.QueryObjectRepository;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 通用查询接口
 * @param <E>
 * @param <ID>
 */
public interface QueryRepository<E, ID> extends QueryObjectRepository<E> {
    default List<E> getByIds(List<ID> ids){
        return Lists.newArrayList(findAllById(ids));
    }

    List<E> findAllById(Iterable<ID> ids);
}
