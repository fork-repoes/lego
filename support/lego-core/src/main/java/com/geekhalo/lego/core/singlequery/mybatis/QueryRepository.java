package com.geekhalo.lego.core.singlequery.mybatis;

import java.util.List;
import java.util.function.Function;

public interface QueryRepository<E> {
    <R,V> List<V> listOf(R request, Function<E, V> converter);

    default <R> List<E> listOf(R request){
        return listOf(request, e->e);
    }

    <R> Long countOf(R request);

    <R, V> V get(R request, Function<E, V> converter);

    default <R> E get(R request) {
        return get(request, e -> e);
    }

//    <R, V> Page<V> pageOf(R request, Function<V, E> converter);
//
//    default <R> Page<E> pageOf(R request){
//        return pageOf(request, e -> e);
//    }
}
