package com.geekhalo.lego.core.query;

import com.geekhalo.lego.core.query.support.handler.converter.ResultConverter;

/**
 * 结构转化器，对查询结果进行封装
 * @param <I>
 * @param <O>
 */
public interface QueryResultConverter<I, O> extends ResultConverter<I, O> {
    /**
     * 是否能支持对应类型的转换
     * @param input
     *      输入类型
     * @param output
     *      输出类型
     * @return
     */
    boolean support(Class<I> input, Class<O> output);

    /**
     * 进行模型转换
     * @param input
     * @return
     */
    @Override
    O convert(I input);
}
