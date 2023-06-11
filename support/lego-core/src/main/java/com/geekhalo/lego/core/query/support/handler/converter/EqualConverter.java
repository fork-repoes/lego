package com.geekhalo.lego.core.query.support.handler.converter;

public class EqualConverter<T> implements ResultConverter<T, T>{
    private static final EqualConverter INSTANCE = new EqualConverter();
    private EqualConverter(){

    }

    @Override
    public T convert(T param) {
        return param;
    }

    @Override
    public String toString(){
        return "EqualConverter";
    }

    public static EqualConverter getInstance(){
        return INSTANCE;
    }

}
