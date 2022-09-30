package com.geekhalo.lego.core.query;

/**
 * 结构转化器，对查询结果进行封装
 * @param <I>
 * @param <O>
 */
public interface ResultConverter<I, O> {
    boolean support(Class<I> input, Class<O> output);

    O convert(I input);
}
