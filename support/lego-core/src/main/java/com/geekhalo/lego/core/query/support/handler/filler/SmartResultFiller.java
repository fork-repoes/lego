package com.geekhalo.lego.core.query.support.handler.filler;

public interface SmartResultFiller<R> extends ResultFiller<R>{
    boolean apply(Class resultClass);
}
