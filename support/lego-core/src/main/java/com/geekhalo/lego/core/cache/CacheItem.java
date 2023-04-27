package com.geekhalo.lego.core.cache;

import lombok.Value;

/**
 * Created by taoli on 2023/4/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class CacheItem<KEY, VALUE> {
    private KEY key;
    private VALUE value;

    public static <KEY, VALUE> CacheItem<KEY, VALUE> of(KEY key, VALUE value){
        return new CacheItem<>(key, value);
    }

    public static <KEY, VALUE> CacheItem<KEY, VALUE> of(KEY key){
        return of(key, null);
    }
}
