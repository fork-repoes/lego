package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by taoli on 2022/8/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JoinInMemoryBasedJoinItemExecutorFactory extends AbstractAnnotationBasedJoinItemExecutorFactory<JoinInMemory>{
    protected JoinInMemoryBasedJoinItemExecutorFactory() {
        super(JoinInMemory.class);
    }


    @Override
    protected <DATA> BiConsumer<Object, Object> createLostFunction(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> BiConsumer<Object, Object> createFoundFunction(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> Function<Object, Object> createDataConverter(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> Function<Object, Object> createKeyGeneratorFromJoinData(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> Function<List<Object>, List<Object>> createDataLoeader(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> Function<Object, Object> createKeyGeneratorFromData(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }

    @Override
    protected <DATA> int createRunLevel(Class<DATA> cls, Field field, JoinInMemory ann) {
        return 0;
    }

    @Override
    protected <DATA> String createName(Class<DATA> cls, Field field, JoinInMemory ann) {
        return null;
    }
}
