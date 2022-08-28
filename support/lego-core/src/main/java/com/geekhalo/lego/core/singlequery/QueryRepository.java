package com.geekhalo.lego.core.singlequery;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/7/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 查询接口
 */
public interface QueryRepository<E> {
    /**
     *
     * @param query 查询参数
     * @param converter 结果转化器
     * @param <Q>
     * @param <R>
     * @return
     */
    <Q, R> List<R> listOf(Q query, Function<E, R> converter);

    /**
     *
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    default <Q> List<E> listOf(Q query){
        return listOf(query, e->e);
    }

    /**
     * 获取数量
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    <Q> Long countOf(Q query);

    /**
     * 获取单条记录
     * @param query 查询参数
     * @param converter 结果转化器
     * @param <Q>
     * @param <R>
     * @return
     */
    <Q, R> R get(Q query, Function<E, R> converter);

    /**
     * 获取单条记录
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    default <Q> E get(Q query) {
        return get(query, e -> e);
    }

    /**
     * 分页查询
     * @param query 查询参数
     * @param converter 结果转换器
     * @param <Q>
     * @param <R>
     * @return
     */
    <Q, R> Page<R> pageOf(Q query, Function<E, R> converter);

    /**
     * 分页查询
     * @param query
     * @param <Q>
     * @return
     */
    default <Q> Page<E> pageOf(Q query){
        return pageOf(query, e -> e);
    }
}
