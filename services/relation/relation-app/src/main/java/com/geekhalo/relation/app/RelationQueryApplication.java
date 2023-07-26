package com.geekhalo.relation.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.relation.domain.RelationQueryRepository;
import com.geekhalo.relation.domain.Relation;

@QueryServiceDefinition(
        repositoryClass = RelationQueryRepository.class,
        domainClass = Relation.class
)
public interface RelationQueryApplication {

}
