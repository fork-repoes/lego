package com.geekhalo.lego.core.idempotent.support;

import com.geekhalo.lego.core.idempotent.IdempotentExecutor;
import com.geekhalo.lego.core.idempotent.IdempotentExecutorFactory;
import com.geekhalo.lego.core.idempotent.IdempotentMeta;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class IdempotentExecutorFactories {
    private final Map<String, IdempotentExecutorFactory> factoryMap = Maps.newHashMap();

    public IdempotentExecutorFactories(Map<String, IdempotentExecutorFactory> factoryMap){
        this.factoryMap.putAll(factoryMap);
    }

    public IdempotentExecutor create(IdempotentMeta meta) {
        if (meta == null){
            return NllIdempotentExecutor.getInstance();
        }

        IdempotentExecutorFactory idempotentExecutorFactory = factoryMap.get(meta.executorFactory());
        if (idempotentExecutorFactory == null){
            log.error("Failed to find IdempotentExecutorFactory for {}", meta.executorFactory());
            return NllIdempotentExecutor.getInstance();
        }
        return idempotentExecutorFactory.create(meta);
    }
}
