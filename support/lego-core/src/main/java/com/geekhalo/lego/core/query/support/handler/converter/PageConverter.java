package com.geekhalo.lego.core.query.support.handler.converter;

import com.geekhalo.lego.core.singlequery.Page;

public class PageConverter implements ResultConverter<Page, Page> {
    private final ResultConverter itemConverter;

    public PageConverter(ResultConverter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Override
    public Page convert(Page page) {
        if (page == null){
            return null;
        }
        return page.convert(t -> this.itemConverter.convert(t));
    }

    @Override
    public String toString() {
        return "PageConverter[" + itemConverter + "]";
    }
}