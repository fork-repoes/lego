package com.geekhalo.lego.feign;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/11/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
public class TestFeignService implements TestFeignApi{
    @Getter(AccessLevel.PRIVATE)
    public Map<String, List<Long>> cache = Maps.newHashMap();

    public List<Long> getByKey(String key){
        return this.cache.get(key);
    }

    @Override
    public void postData(String key, List<Long> data) {
        this.cache.put(key, data);
    }

    @Override
    public void postDataForError(String key, List<Long> data) {
        throw new RuntimeException();
    }

    @Override
    public List<Long> getData(String key) {
        return this.cache.get(key);
    }

    @Override
    public List<Long> getDataForError(String key) {
        throw new RuntimeException();
    }
}
