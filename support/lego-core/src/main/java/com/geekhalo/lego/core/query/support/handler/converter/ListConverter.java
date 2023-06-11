package com.geekhalo.lego.core.query.support.handler.converter;

import java.util.List;
import java.util.stream.Collectors;

public class ListConverter implements ResultConverter<List, List>{
    private final ResultConverter itemConverter;

    public ListConverter(ResultConverter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Override
    public List converter(List param) {
        return (List) param.stream()
                .map(t -> itemConverter.converter(t))
                .collect(Collectors.toList());
    }
}