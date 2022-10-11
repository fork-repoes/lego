package com.geekhalo.lego.core.support.proxy;

/**
 * Created by taoli on 2022/10/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultProxyObject implements ProxyObject{
    private final Class itf;

    public DefaultProxyObject(Class itf) {
        this.itf = itf;
    }

    @Override
    public Class getInterface() {
        return this.itf;
    }
}
