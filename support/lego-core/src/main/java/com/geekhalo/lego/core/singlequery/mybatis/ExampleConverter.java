package com.geekhalo.lego.core.singlequery.mybatis;


import com.geekhalo.lego.core.singlequery.Pageable;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 将查询对象转换为 Example 对象
 */
public interface ExampleConverter<E> {
    E convertForQuery(Object query);

    E convertForCount(Object query);

    Pageable findPageable(Object query);

    void validate(Class cls);
}
