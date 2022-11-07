package com.geekhalo.lego.idempotent;

import com.geekhalo.lego.annotation.idempotent.Idempotent;
import com.geekhalo.lego.annotation.idempotent.IdempotentHandleType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class DBBasedIdempotentService
        extends BaseIdempotentService{

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putForResult(String key, Long data){
        return put(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
        handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putForError(String key, Long data){
        return put(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putExceptionForResult(String key, Long data) {
        return putException(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putExceptionForError(String key, Long data) {
        return putException(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.RESULT)
    @Transactional
    public Long putWaitForResult(String key, Long data) {
        return putForWait(key, data);
    }

    @Override
    @Idempotent(executorFactory = "dbExecutorFactory", group = 1, keyEl = "#key",
            handleType = IdempotentHandleType.ERROR)
    @Transactional
    public Long putWaitForError(String key, Long data) {
        return putForWait(key, data);
    }
}
