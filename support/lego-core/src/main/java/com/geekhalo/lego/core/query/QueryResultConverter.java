package com.geekhalo.lego.core.query;

/**
 * 结构转化器，对查询结果进行封装
 * @param <I>
 * @param <O>
 */
public interface QueryResultConverter<I, O> {
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
    O convert(I input);
}
