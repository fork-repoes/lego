package com.geekhalo.lego.cache;

import com.geekhalo.lego.annotation.cache.BatchCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class BatchCacheableAspect {
    
    @Autowired
    private CacheManager cacheManager;
    
    @Around("@annotation(batchCacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, BatchCacheable batchCacheable) throws Throwable {
        String cacheName = batchCacheable.value();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Unknown cache name " + cacheName);
        }
        
        Object[] arguments = joinPoint.getArgs();
        List<Object> keys = getKeys(arguments, batchCacheable.key());
        List<Object> values = new ArrayList<>(keys.size());
        Map<Object, Object> valueMap = new LinkedHashMap<>(keys.size());
        
        for (Object key : keys) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                valueMap.put(key, valueWrapper.get());
            } else {
                values.add(key);
            }
        }
        
        if (values.isEmpty()) {
            return getReturnObject(joinPoint, valueMap, arguments, batchCacheable);
        } else {
            Object returnObject = joinPoint.proceed();
            if (returnObject != null) {
                Collection<?> objects = Collection.class.isAssignableFrom(returnObject.getClass())
                        ? (Collection<?>) returnObject
                        : Arrays.asList(returnObject);
                        
                for (Object object : objects) {
                    Object key = getKey(object, batchCacheable.key());
                    if (key != null) {
                        valueMap.put(key, object);
                        cache.put(key, object);
                    }
                }
            }
            return getReturnObject(joinPoint, valueMap, arguments, batchCacheable);
        }
    }
    
    private List<Object> getKeys(Object[] arguments, String keyExpression) {
        List<Object> keys = new ArrayList<>(arguments.length);
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        for (Object argument : arguments) {
            Object key = parser.parseExpression(keyExpression).getValue(context, argument);
            keys.add(key);
        }
        return keys;
    }
    
    private Object getKey(Object object, String keyExpression) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(object);
        return parser.parseExpression(keyExpression).getValue(context);
    }
    
    private Object getReturnObject(ProceedingJoinPoint joinPoint, Map<Object, Object> valueMap, Object[] arguments, BatchCacheable batchCacheable) throws Throwable {
        Object returnObject = joinPoint.proceed();
        if (returnObject != null) {
            Collection<Object> returnObjects = Collection.class.isAssignableFrom(returnObject.getClass())
                    ? (Collection<Object>) returnObject
                    : Arrays.asList(returnObject);
                    
            List<Object> results = new ArrayList<>(returnObjects.size());
            for (Object argument : arguments) {
                Object key = getKey(argument, batchCacheable.key());
                results.add(valueMap.get(key));
            }
            returnObject = Collection.class.isAssignableFrom(returnObject.getClass())
                    ? results
                    : results.get(0);
        }
        return returnObject;
    }
}