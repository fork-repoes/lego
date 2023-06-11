package com.geekhalo.lego.core.query.support.handler.filler;

import com.geekhalo.lego.core.joininmemory.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(Integer.MAX_VALUE)
public class JoinBasedResultFiller<R> implements SmartResultFiller<R>{
    @Autowired
    private JoinService joinService;

    @Override
    public R fill(R r) {
        if (r != null){
            joinService.joinInMemory(r);
        }
        return r;
    }

    @Override
    public boolean apply(Class resultClass) {
        return true;
    }

    @Override
    public String toString() {
        return "JoinInMemory";
    }
}
