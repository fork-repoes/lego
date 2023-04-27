package com.geekhalo.lego.core.cache;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Value;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2023/4/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class CacheItems<KEY, VALUE> {
    private Map<KEY, VALUE> result = Maps.newHashMap();


    public Set<VALUE> getAllValues(){
        return Sets.newHashSet(result.values());
    }

    public CacheItem getByKey(KEY key){
        VALUE value = this.result.get(key);
        return CacheItem.of(key, value);
    }

    public VALUE getValueByKey(KEY key){
        return this.result.get(key);
    }

    public Set<KEY> getMissKeys(){
        return result.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }
}
