package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.google.common.base.Preconditions;
import lombok.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class QueryServiceMetadata {
    private Class domainClass;

    private Class repositoryClass;

    private Class queryServiceClass;

    public static QueryServiceMetadata fromQueryService(Class queryService){
        Preconditions.checkArgument(queryService != null);

        QueryServiceDefinition definition = AnnotatedElementUtils.findMergedAnnotation(queryService, QueryServiceDefinition.class);
        if (definition == null){
            return null;
        }

        Class domainClass = definition.domainClass();
        Class repositoryClass = definition.repositoryClass();

        Preconditions.checkArgument(domainClass != null);
        Preconditions.checkArgument(repositoryClass != null);

        return new QueryServiceMetadata(domainClass, repositoryClass, queryService);
    }
}
