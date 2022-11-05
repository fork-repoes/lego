package com.geekhalo.lego.core.idempotent.support;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface ExecutionRecordRepository {
    void update(ExecutionRecord executionRecord);

    ExecutionRecord getOrCreate(int type, String key);
}
