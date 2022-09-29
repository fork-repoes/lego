package com.geekhalo.lego.core.query;

public interface ResultConverter<I, O> {
    boolean support(Class<I> input, Class<O> output);

    O convert(I input);
}
