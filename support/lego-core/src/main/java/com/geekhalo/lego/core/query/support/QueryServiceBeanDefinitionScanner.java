package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.query.NoQueryService;
import com.geekhalo.lego.core.query.QueryServiceDefinition;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.NonNull;

import java.util.Set;

public class QueryServiceBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public QueryServiceBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(false);
        this.registry = registry;

        super.addIncludeFilter(new AnnotationTypeFilter(QueryServiceDefinition.class, true, true));
        addExcludeFilter(new AnnotationTypeFilter(NoQueryService.class));
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {

        Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);

        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }

        return candidates;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean isInterface = beanDefinition.getMetadata().isInterface();
        boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        return isInterface && isTopLevelType;
    }

    @NonNull
    @Override
    protected BeanDefinitionRegistry getRegistry() {
        return registry;
    }
}
