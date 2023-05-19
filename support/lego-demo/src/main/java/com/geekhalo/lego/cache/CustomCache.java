package com.geekhalo.lego.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class CustomCache implements Cache {
    private final Cache cache;

    CustomCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        if (key instanceof Collection) { // 判断key是否为集合类型
            List<Object> result = new ArrayList<>();
            for (Object obj : (Collection) key) {
                ValueWrapper valueWrapper = cache.get(obj);
                if (valueWrapper != null) {
                    result.addAll((List<Object>) valueWrapper.get());
                }
            }
            if (result.isEmpty()){
                return null;
            }else {
                return new SimpleValueWrapper(result);
            }
        } else {
            return cache.get(key);
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return this.cache.get(key, type);
//        if (key instanceof Collection) { // 判断key是否为集合类型
//            List<Object> result = new ArrayList<>();
//            for (Object obj : (Collection) key) {
//                T value = cache.get(obj, type);
//                if (value != null) {
//                    result.add(value);
//                }
//            }
//            return result;
//        } else {
//            return cache.get(key);
//        }
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return this.cache.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        if (key instanceof Collection) { // 判断key是否为集合类型
            for (Object obj : (Collection) key) {
                Object cachedValue = cache.get(obj);
                if (cachedValue != null) {
                    List<Object> newList = new ArrayList<>((List<Object>) cachedValue);
                    newList.addAll((List<Object>) value);
                    value = newList;
                }
                cache.put(obj, value);
            }
        } else {
            cache.put(key, value);
        }
    }

    @Override
    public void evict(Object key) {
        if (key instanceof Collection) { // 判断key是否为集合类型
            for (Object obj : (Collection) key) {
                cache.evict(obj);
            }
        } else {
            cache.evict(key);
        }
    }

    @Override
    public void clear() {
        cache.clear();
    }
}