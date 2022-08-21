package com.geekhalo.lego.core.loader;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
