package com.geekhalo.lego.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCacheManager extends AbstractCacheManager {
    private CacheManager cacheManager;

    public CustomCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.cacheManager.getCacheNames().stream()
                .map(cacheName -> this.cacheManager.getCache(cacheName))
                .map(cache -> new CustomCache(cache))
                .collect(Collectors.toList());
//        for (CacheNames cacheName : CacheNames.values()) {
//            caches.add(new CustomCache(cacheName, redisTemplate, objectMapper));
//        }
//        return caches;
    }
}