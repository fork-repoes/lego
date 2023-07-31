package com.geekhalo.relation.domain.group;


import com.geekhalo.lego.core.query.QueryRepository;

public interface RelationGroupQueryRepository extends QueryRepository<RelationGroup, Long> {

    RelationGroup getById(Long id);
}
