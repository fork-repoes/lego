package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.annotation.singlequery.MaxResultCheckStrategy;
import com.geekhalo.lego.core.singlequery.*;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.mybatis.support.AnnoBasedMaxResultConfigResolver;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationQueryRepository;
import com.geekhalo.lego.core.singlequery.support.AbstractQueryRepository;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.google.common.collect.Lists;

import javax.annotation.PostConstruct;
import java.util.Collections;
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
    extends AbstractQueryRepository<E>
    implements SpecificationQueryRepository<E> {

    private final JpaSpecificationExecutor<E> specificationExecutor;
    private final Class<E> entityCls;

    @Autowired
    private SpecificationConverterFactory specificationConverterFactory;

    @Getter(AccessLevel.PROTECTED)
    private SpecificationConverter<E> specificationConverter;
    private MaxResultConfigResolver maxResultConfigResolver;

    public AbstractSpecificationQueryRepository(JpaSpecificationExecutor<E> specificationExecutor, Class<E> entityCls) {
        Preconditions.checkArgument(specificationExecutor != null);
        Preconditions.checkArgument(entityCls != null);
        this.specificationExecutor = specificationExecutor;
        this.entityCls = entityCls;
    }

    @Override
    protected Object getDao() {
        return this.specificationExecutor;
    }

    @PostConstruct
    public void init(){
        if (this.specificationConverter == null) {
            this.specificationConverter = this.specificationConverterFactory.createForEntity(entityCls);
        }

        if (maxResultConfigResolver == null){
            this.maxResultConfigResolver = new AnnoBasedMaxResultConfigResolver();
        }

        Preconditions.checkArgument(this.specificationConverter != null);
        Preconditions.checkArgument(this.maxResultConfigResolver != null);
    }


    @Override
    public <Q, R> List<R> listOf(Q query, Function<E, R> converter) {
        Specification<E> specification = this.specificationConverter.convertForQuery(query);
        if (specification == null){
            return Lists.newArrayList();
        }

        org.springframework.data.domain.Sort springSort = createSpringSort(query);

        MaxResultConfig maxResultConfig = this.maxResultConfigResolver.maxResult(query);

        org.springframework.data.domain.Pageable springPageable = null;
        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            if (springSort != null) {
                springPageable = PageRequest.of(0, maxResultConfig.getMaxResult(), springSort);
            }else {
                springPageable = PageRequest.of(0, maxResultConfig.getMaxResult());
            }
        }

        List<E> entities = null;
        if (springPageable != null){
            entities = this.specificationExecutor.findAll(specification, springPageable).getContent();
        }else if (springSort != null) {
            entities = this.specificationExecutor.findAll(specification, springSort);
        }else {
            entities = this.specificationExecutor.findAll(specification);
        }

        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        processForMaxResult(query, maxResultConfig, entities);

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
        Pageable pageable = findPageable(query);
        if (pageable == null){
            throw new IllegalArgumentException("Pageable Lost");
        }

        Specification<E> specification = this.specificationConverter.convertForQuery(query);
        if (specification == null){
            return Page.nullObject(pageable);
        }

        org.springframework.data.domain.Sort springSort = createSpringSort(query);

        org.springframework.data.domain.Pageable springPageable = null;
        if (springSort != null) {
            springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize(), springSort);
        }else {
            springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize());
        }

        org.springframework.data.domain.Page<E> entityPage = this.specificationExecutor.findAll(specification, springPageable);

        List<R> entities = entityPage.getContent().stream()
                .filter(Objects::nonNull)
                .map(converter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new Page(entities, pageable, entityPage.getTotalElements());
    }

    private <Q> org.springframework.data.domain.Sort createSpringSort(Q query) {
        Sort sort = this.specificationConverter.findSort(query);
        if (sort == null || CollectionUtils.isEmpty(sort.getOrders())){
            return null;
        }

        List<org.springframework.data.domain.Sort.Order> springOrders = convertToSpringOrders(sort.getOrders());

        return org.springframework.data.domain.Sort.by(springOrders);
    }

    private List<org.springframework.data.domain.Sort.Order> convertToSpringOrders(List<Sort.Order> orders) {
        List<org.springframework.data.domain.Sort.Order> springOrders =
                Lists.newArrayListWithCapacity(orders.size());
        for (Sort.Order order : orders){
            Sort.Direction direction = order.getDirection();
            OrderField orderField = (OrderField)order.getOrderField();
            if (direction == null || orderField == null){
                continue;
            }
            if (direction == Sort.Direction.ASC) {
                springOrders.add(org.springframework.data.domain.Sort.Order.asc(orderField.getColumnName()));
            }
            if (direction == Sort.Direction.DESC){
                springOrders.add(org.springframework.data.domain.Sort.Order.desc(orderField.getColumnName()));
            }
        }
        return springOrders;
    }


    private  <Q> Pageable findPageable(Q query) {
        return this.specificationConverter.findPageable(query);
    }
}
