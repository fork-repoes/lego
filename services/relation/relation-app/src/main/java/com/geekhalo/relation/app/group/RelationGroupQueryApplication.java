package com.geekhalo.relation.app.group;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupQueryRepository;

import java.util.List;

@QueryServiceDefinition(
        repositoryClass = RelationGroupQueryRepository.class,
        domainClass = RelationGroup.class
)
public interface RelationGroupQueryApplication {
    RelationGroup getById(Long id);

    List<RelationGroup> listOf(QueryGroupByOwner queryGroupByOwner);

}
