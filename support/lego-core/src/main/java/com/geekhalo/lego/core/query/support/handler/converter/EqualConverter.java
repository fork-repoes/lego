package com.geekhalo.lego.core.query.support.handler.converter;

public class EqualConverter<T> implements ResultConverter<T, T>{
    private static final EqualConverter INSTANCE = new EqualConverter();
    private EqualConverter(){

    }

    @Override
    public T converter(T param) {
        return param;
    }

    public static EqualConverter getInstance(){
        return INSTANCE;
    }
}
