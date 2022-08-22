package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.annotation.singlequery.MaxResultCheckStrategy;
import com.geekhalo.lego.core.singlequery.mybatis.*;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractReflectBasedExampleQueryRepository<E> implements ExampleQueryRepository<E> {
    private final Object mapper;
    private final ExampleConverter exampleConverter;
    private final MaxResultConfigResolver maxResultConfigResolver;

    public AbstractReflectBasedExampleQueryRepository(Object mapper, Class exampleClass){
        this(mapper, new ReflectBasedExampleConverter<>(exampleClass), new AnnoBasedMaxResultConfigResolver());
    }

    public AbstractReflectBasedExampleQueryRepository(Object mapper,
                                                      ExampleConverter exampleConverter,
                                                      MaxResultConfigResolver maxResultConfigResolver) {
        Preconditions.checkArgument(mapper != null);
        Preconditions.checkArgument(exampleConverter != null);
        Preconditions.checkArgument(maxResultConfigResolver != null);
        this.exampleConverter = exampleConverter;
        this.mapper = mapper;
        this.maxResultConfigResolver = maxResultConfigResolver;
    }

    @Override
    public <R, V> List<V> listOf(R request, Function<E, V> converter) {
        Preconditions.checkArgument(converter != null);
        if (request == null){
            return Collections.emptyList();
        }

        Object example = this.exampleConverter.convert(request);

        MaxResultConfig maxResultConfig = this.maxResultConfigResolver.maxResult(request);

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            setLimitForExample(maxResultConfig.getMaxResult(), example);
        }

        List<E> entities = doList(example);

        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        processForMaxResult(request, maxResultConfig, entities);

        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .collect(Collectors.toList());
    }

    private void processForMaxResult(Object request, MaxResultConfig maxResultConfig, List<E> entities) {
        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.LOG){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.warn("【LOG】result size is {} more than {}, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        request);
                return;
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.ERROR){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【ERROR】result size is {} more than {}, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        request);
                throw new ManyResultException();
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【SET_LIMIT】result size is {} more than {}, please find and fix, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        request);
                return;
            }
        }
    }

    private void setLimitForExample(Integer maxResult, Object example) {
        try {
            MethodUtils.invokeMethod(example, true, "setLimit", maxResult);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "setLimit", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public <R, V> V get(R request, Function<E, V> converter) {
        Preconditions.checkArgument(converter != null);
        if (request == null){
            return null;
        }
        Object example = this.exampleConverter.convert(request);
        List<E> entities = doList(example);
        if (CollectionUtils.isEmpty(entities)){
            return null;
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter).findAny().orElse(null);
    }

    private List<E> doList(Object example){
        try {
            return (List<E>) MethodUtils.invokeMethod(this.mapper, true, "selectByExample", example);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "selectByExample", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <R> Long countOf(R request) {
        if (request == null){
            return null;
        }
        Object example = this.exampleConverter.convert(request);
        return doCount(example);
    }

    private Long doCount(Object example) {
        try {
            return (Long) MethodUtils.invokeMethod(this.mapper, true, "countByExample", example);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "countByExample", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }

}
