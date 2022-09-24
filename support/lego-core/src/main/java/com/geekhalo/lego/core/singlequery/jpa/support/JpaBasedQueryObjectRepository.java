package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationQueryObjectRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaBasedQueryObjectRepository<E, ID>
        extends SimpleJpaRepository<E, ID>
        implements SpecificationQueryObjectRepository<E> {

    private final BaseSpecificationQueryObjectRepository<E> specificationQueryObjectRepository;

    public JpaBasedQueryObjectRepository(JpaEntityInformation<E, ?> entityInformation,
                                         EntityManager entityManager,
                                         SpecificationConverterFactory specificationConverterFactory) {
        super(entityInformation, entityManager);
        this.specificationQueryObjectRepository =
                new BaseSpecificationQueryObjectRepository(this,
                        entityInformation.getJavaType(),
                        specificationConverterFactory);
    }

    public JpaBasedQueryObjectRepository(Class<E> domainClass, EntityManager em, SpecificationConverterFactory specificationConverterFactory) {
        super(domainClass, em);
        this.specificationQueryObjectRepository = new BaseSpecificationQueryObjectRepository(this,
                domainClass,
                specificationConverterFactory);
    }

    @Override
    public <Q> List<E> listOf(Q query) {
        return this.specificationQueryObjectRepository.listOf(query);
    }

    @Override
    public <Q> Long countOf(Q query) {
        return this.specificationQueryObjectRepository.countOf(query);
    }

    @Override
    public <Q> E get(Q query) {
        return this.specificationQueryObjectRepository.get(query);
    }

    @Override
    public <Q> Page<E> pageOf(Q query) {
        return this.specificationQueryObjectRepository.pageOf(query);
    }

    public void init(){
        this.specificationQueryObjectRepository.init();
    }
}
