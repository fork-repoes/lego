package com.geekhalo.lego.core.singlequery.mybatis;


public interface ExampleConverter<E> {
    E convert(Object r);

    void testInput(Class cls);
}
