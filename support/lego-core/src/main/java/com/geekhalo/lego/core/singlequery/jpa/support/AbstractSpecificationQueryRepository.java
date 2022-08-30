package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.core.singlequery.MaxResultConfigResolver;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.mybatis.support.AnnoBasedMaxResultConfigResolver;
import com.geekhalo.lego.core.singlequery.support.AbstractQueryRepository;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationQueryRepository;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.google.common.collect.Lists;

import javax.annotation.PostConstruct;
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



    protected <Q> Pageable findPageable(Q query) {
        Pageable pageable = this.specificationConverter.findPageable(query);
        return pageable;
    }
}
