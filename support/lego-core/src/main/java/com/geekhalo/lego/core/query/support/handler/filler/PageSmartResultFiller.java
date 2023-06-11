package com.geekhalo.lego.core.query.support.handler.filler;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.core.singlequery.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(1)
public class PageSmartResultFiller implements SmartResultFiller<Page>{
    @Autowired
    private JoinService joinService;

    @Override
    public Page fill(Page page) {
        if (page != null && page.hasContent()){
            joinService.joinInMemory(page.getContent());
        }
        return page;
    }

    @Override
    public boolean apply(Class resultClass) {
        return Page.class.isAssignableFrom(resultClass);
    }

    @Override
    public String toString() {
        return "PageSmartResultFiller[JoinInMemory]";
    }
}
