package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.annotation.singlequery.MaxResultCheckStrategy;
import com.geekhalo.lego.core.singlequery.*;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverter;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverterFactory;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleQueryRepository;
import com.geekhalo.lego.core.singlequery.support.AbstractQueryRepository;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractReflectBasedExampleQueryRepository<E>
        extends AbstractQueryRepository<E>
        implements ExampleQueryRepository<E> {
    private final Object mapper;
    private final Class exampleCls;
    @Getter(AccessLevel.PROTECTED)
    private ExampleConverter exampleConverter;
    private MaxResultConfigResolver maxResultConfigResolver;

    @Autowired
    private ExampleConverterFactory exampleConverterFactory;

    public AbstractReflectBasedExampleQueryRepository(Object mapper, Class exampleClass){
        Preconditions.checkArgument(mapper != null);
        Preconditions.checkArgument(exampleClass != null);
        this.mapper = mapper;
        this.exampleCls = exampleClass;
    }

    @PostConstruct
    public void init(){
        if (exampleConverter == null){
            this.exampleConverter = exampleConverterFactory.createFor(this.exampleCls);
        }
        if (maxResultConfigResolver == null){
            this.maxResultConfigResolver = new AnnoBasedMaxResultConfigResolver();
        }

        Preconditions.checkArgument(this.exampleConverter != null);
        Preconditions.checkArgument(this.maxResultConfigResolver != null);
    }


    @Override
    public <Q, R> List<R> listOf(Q query, Function<E, R> converter) {
        Preconditions.checkArgument(converter != null);

        if (query == null){
            return Collections.emptyList();
        }

        Object example = this.exampleConverter.convertForQuery(query);

        MaxResultConfig maxResultConfig = this.maxResultConfigResolver.maxResult(query);

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            setLimitForExample(maxResultConfig.getMaxResult(), example);
        }

        List<E> entities = doList(example);

        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        processForMaxResult(query, maxResultConfig, entities);

        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .collect(Collectors.toList());
    }

    private void processForMaxResult(Object query, MaxResultConfig maxResultConfig, List<E> entities) {
        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.LOG){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.warn("【LOG】result size is {} more than {}, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        query);
                return;
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.ERROR){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【ERROR】result size is {} more than {}, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        query);
                throw new ManyResultException();
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【SET_LIMIT】result size is {} more than {}, please find and fix, mapper is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.mapper,
                        query);
                return;
            }
        }
    }

    private void setLimitForExample(Integer maxResult, Object example) {
        try {
            MethodUtils.invokeMethod(example, true, "setRows", maxResult);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "setRows", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public <Q, R> R get(Q query, Function<E, R> converter) {
        Preconditions.checkArgument(converter != null);

        if (query == null){
            return null;
        }

        Object example = this.exampleConverter.convertForQuery(query);

        List<E> entities = doList(example);

        if (CollectionUtils.isEmpty(entities)){
            return null;
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .findAny()
                .orElse(null);
    }

    @Override
    protected <Q> Pageable findPageable(Q query){
        return exampleConverter.findPageable(query);
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

        Object example = this.exampleConverter.convertForCount(request);

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
