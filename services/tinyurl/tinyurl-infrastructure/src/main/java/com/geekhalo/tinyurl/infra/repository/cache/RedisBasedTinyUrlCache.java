package com.geekhalo.tinyurl.infra.repository.cache;

import com.geekhalo.tinyurl.domain.TinyUrl;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RedisBasedTinyUrlCache implements TinyUrlCache{
    private static final String LUA_INCR_SCRIPT =
            "local is_exists = redis.call('HEXISTS', KEYS[1], ARGV[1])\n"
            + "if is_exists == 1 then\n"
            + "    return redis.call('HINCRBY', KEYS[1], ARGV[1], ARGV[2])\n"
            + "else\n"
            + "    return nil\n"
            + "end";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${tinyurl.cache.key:tinyurl-{id}}")
    private String cacheKey;

    private ConversionService conversionService = new DefaultConversionService();

    @Override
    public Optional<TinyUrl> findById(Long id) {
        String key = createKey(id);
        Map<String, Object> entries = redisTemplate.<String, Object>opsForHash().entries(key);
        return Optional.ofNullable(entries)
                .map(data -> buildFromMap(entries));

    }

    private TinyUrl buildFromMap(Map<String, Object> entries) {
        if (MapUtils.isEmpty(entries)){
            return null;
        }
        TinyUrl tinyUrl = new TinyUrl();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(tinyUrl);
        beanWrapper.setConversionService(this.conversionService);
        beanWrapper.setPropertyValues(entries);
        return tinyUrl;
    }

    @Override
    public void put(TinyUrl tinyUrl) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(tinyUrl);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Map<String, String> result = Maps.newHashMapWithExpectedSize(propertyDescriptors.length);

        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String fieldName = descriptor.getName();
            if ("class".equals(fieldName)){
                continue;
            }
            if ("events".equals(fieldName)){
                continue;
            }
            Object fieldValue = beanWrapper.getPropertyValue(fieldName);
            if (fieldValue != null) {
                String strValue = this.conversionService.convert(fieldValue, String.class);
                result.put(fieldName, strValue);
            }
        }

        String cacheKey = createKey(tinyUrl.getId());
        this.redisTemplate.opsForHash().putAll(cacheKey, result);
    }

    @Override
    public void incrAccessCount(Long id, int times) {
        String key = createKey(id);
        RedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_INCR_SCRIPT, Long.class);

        List<String> keys = Arrays.asList(key);
        this.redisTemplate.execute(redisScript, keys, "accessCount", String.valueOf(times));
    }

    private String createKey(Long id){
        return cacheKey.replace("{id}", String.valueOf(id));
    }
}
