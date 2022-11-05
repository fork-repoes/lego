package com.geekhalo.lego.idempotent;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class BaseIdempotentService {
    private Map<String, Long> data = Maps.newHashMap();

    public void clean(){
        this.data.clear();
    }

    public Long getValue(String key){
        return this.data.get(key);
    }

    protected Long put(String key, Long data){
         this.data.put(key, data);
         return data;
    }

    protected Long putForWait(String key, Long data){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return put(key, data);
    }


    public abstract Long putForResult(String key, Long data);

    public abstract Long putForError(String key, Long data);

    public abstract Long putWaitForResult(String key, Long data);

    public abstract Long putWaitForError(String key, Long data);
}
