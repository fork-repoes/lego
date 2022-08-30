package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationQueryRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class AbstractSpecificationQueryRepository<E>
    implements SpecificationQueryRepository<E> {

    private final JpaSpecificationExecutor<E> specificationExecutor;

    @Autowired
    @Getter(AccessLevel.PROTECTED)
    private SpecificationConverter<E> specificationConverter;

    public AbstractSpecificationQueryRepository(JpaSpecificationExecutor<E> specificationExecutor) {
        this.specificationExecutor = specificationExecutor;
    }


    @Override
    public <Q, R> List<R> listOf(Q query, Function<E, R> converter) {
        Specification<E> specification = this.specificationConverter.convertForQuery(query);
        if (specification == null){
            return Lists.newArrayList();
        }
        List<E> entities = this.specificationExecutor.findAll(specification);
        if (CollectionUtils.isEmpty(entities)){
            return null;
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .map(entity -> converter.apply(entity))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public <Q> Long countOf(Q query) {
        Specification<E> specification = this.specificationConverter.convertForCount(query);
        if (specification == null){
            return 0L;
        }
        return this.specificationExecutor.count(specification);
    }

    @Override
    public <Q, R> R get(Q query, Function<E, R> converter) {
        Specification<E> specification = this.specificationConverter.convertForQuery(query);
        if (specification == null){
            return null;
        }
        Optional<E> entity = this.specificationExecutor.findOne(specification);
        return entity
                .map(converter)
                .orElse(null);
    }

    @Override
    public <Q, R> Page<R> pageOf(Q query, Function<E, R> converter) {
        Pageable pageable = this.specificationConverter.findPageable(query);

        if (pageable == null){
            throw new IllegalArgumentException("Pageable Lost");
        }

        Long totalElement = countOf(query);

        List<R> content =  listOf(query, converter);

        return new Page<>(content, pageable, totalElement);
    }
}
