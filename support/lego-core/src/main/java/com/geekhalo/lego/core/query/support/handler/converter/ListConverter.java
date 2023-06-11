package com.geekhalo.lego.core.query.support.handler.converter;

import java.util.List;
import java.util.stream.Collectors;

public class ListConverter implements ResultConverter<List, List>{
    private final ResultConverter itemConverter;

    public ListConverter(ResultConverter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Override
    public List convert(List param) {
        return (List) param.stream()
                .map(t -> itemConverter.convert(t))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ListConverter[" + itemConverter +"]";
    }
}