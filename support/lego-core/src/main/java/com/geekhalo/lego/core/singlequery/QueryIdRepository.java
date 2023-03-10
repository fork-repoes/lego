package com.geekhalo.lego.core.singlequery;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface QueryIdRepository<E, ID> {
    default <R> R getById(ID id, Function<E, R> converter){
        E e = getById(id);
        if (e == null){
            return null;
        }
        return converter.apply(e);
    }

    E getById(ID id);

    default <R> List<R> getByIds(List<ID> ids, Function<E, R> converter){
        List<E> entities = getByIds(ids);
        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<E> getByIds(List<ID> ids);
}
