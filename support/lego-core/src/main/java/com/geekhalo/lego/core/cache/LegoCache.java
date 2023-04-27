package com.geekhalo.lego.core.cache;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

/**
 * Created by taoli on 2023/4/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface LegoCache<KEY, VALUE> {
    String name();

    CacheItem<KEY, VALUE> get(KEY key);

    void put(KEY key, @Nullable VALUE value);

    void evict(KEY key);
}
