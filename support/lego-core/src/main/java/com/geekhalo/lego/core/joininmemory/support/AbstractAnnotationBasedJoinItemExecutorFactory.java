package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutorFactory;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractAnnotationBasedJoinItemExecutorFactory<A extends Annotation>
    implements JoinItemExecutorFactory {
    private final Class<A> annotationCls;

    protected AbstractAnnotationBasedJoinItemExecutorFactory(Class<A> annotationCls) {
        this.annotationCls = annotationCls;
    }

    @Override
    public <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls) {
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(cls, this.annotationCls);
        return fieldsListWithAnnotation.stream()
                .map(field -> createForField(cls, field, field.getAnnotation(this.annotationCls)))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private <DATA> JoinItemExecutor<DATA> createForField(Class<DATA> cls, Field field,A ann) {
        JoinItemExecutorAdapter adapter = JoinItemExecutorAdapter.builder()
                .name(createName(cls, field, ann))
                .runLevel(createRunLevel(cls, field, ann))
                .keyGeneratorFromData(createKeyGeneratorFromData(cls, field, ann))
                .dataLoeader(createDataLoeader(cls, field, ann))
                .keyGeneratorFromJoinData(createKeyGeneratorFromJoinData(cls, field, ann))
                .dataConverter(createDataConverter(cls, field, ann))
                .foundFunction(createFoundFunction(cls, field, ann))
                .lostFunction(createLostFunction(cls, field, ann))
                .build();

        return adapter;
    }

    protected abstract <DATA> BiConsumer<Object, Object> createLostFunction(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> BiConsumer<Object, Object> createFoundFunction(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createDataConverter(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createKeyGeneratorFromJoinData(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<List<Object>, List<Object>> createDataLoeader(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createKeyGeneratorFromData(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> int createRunLevel(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> String createName(Class<DATA> cls, Field field, A ann);
}
