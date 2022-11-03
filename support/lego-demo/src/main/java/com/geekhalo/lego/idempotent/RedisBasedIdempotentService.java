package com.geekhalo.lego.idempotent;

import com.geekhalo.lego.annotation.idempotent.Idempotent;
import com.geekhalo.lego.annotation.idempotent.IdempotentHandleType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class RedisBasedIdempotentService
        extends BaseIdempotentService{

    @Idempotent(executorName = "redisExecutor", group = 1, key = "#{key}",
            handleType = IdempotentHandleType.RESULT)
    @Override
    public Long putForResult(String key, Long data){
        return put(key, data);
    }

    @Idempotent(executorName = "redisExecutor", group = 1, key = "#{key}",
        handleType = IdempotentHandleType.ERROR)
    @Override
    public Long putForError(String key, Long data){
        return put(key, data);
    }

    @Override
    @Idempotent(executorName = "redisExecutor", group = 1, key = "#{key}",
            handleType = IdempotentHandleType.RESULT)
    public Long putWaitForResult(String key, Long data) {
        return putForWait(key, data);
    }

    @Override
    @Idempotent(executorName = "redisExecutor", group = 1, key = "#{key}",
            handleType = IdempotentHandleType.ERROR)
    public Long putWaitForError(String key, Long data) {
        return putForWait(key, data);
    }
}
