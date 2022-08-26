package com.geekhalo.lego.core.singlequery.mybatis;


import com.geekhalo.lego.core.singlequery.Pageable;

public interface ExampleConverter<E> {
    E convert(Object r);

    Pageable findPageable(Object r);

    void testInput(Class cls);
}
