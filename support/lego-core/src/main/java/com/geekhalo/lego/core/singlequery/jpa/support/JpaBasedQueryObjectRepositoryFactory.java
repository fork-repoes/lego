package com.geekhalo.lego.core.singlequery.jpa.support;

import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaBasedQueryObjectRepositoryFactory extends JpaRepositoryFactory {
    private final SpecificationConverterFactory specificationConverterFactory;
    public JpaBasedQueryObjectRepositoryFactory(EntityManager entityManager,
                                                SpecificationConverterFactory specificationConverterFactory) {
        super(entityManager);
        this.specificationConverterFactory = specificationConverterFactory;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
        Object repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager, specificationConverterFactory);

        Assert.isInstanceOf(JpaBasedQueryObjectRepository.class, repository);
        ((JpaBasedQueryObjectRepository) repository).init();
        return (JpaRepositoryImplementation<?, ?>) repository;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return JpaBasedQueryObjectRepository.class;
    }
}
