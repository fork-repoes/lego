package com.geekhalo.lego.core.idempotent.support;

import com.geekhalo.lego.core.idempotent.IdempotentExecutor;
import com.geekhalo.lego.core.idempotent.IdempotentExecutorFactory;
import com.geekhalo.lego.core.idempotent.IdempotentMeta;
import lombok.Setter;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Setter
public class SimpleIdempotentExecutorFactory implements IdempotentExecutorFactory {
    private IdempotentKeyParser idempotentKeyParser;
    private ExecutionResultSerializer serializer;
    private ExecutionRecordRepository executionRecordRepository;

    @Override
    public IdempotentExecutor create(IdempotentMeta meta) {
        return new SimpleIdempotentExecutor(meta,
                idempotentKeyParser,
                this.serializer,
                this.executionRecordRepository);
    }
}
