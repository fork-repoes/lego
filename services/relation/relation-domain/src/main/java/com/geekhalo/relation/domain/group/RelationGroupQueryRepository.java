package com.geekhalo.relation.domain.group;


import com.geekhalo.lego.core.query.QueryRepository;
import com.geekhalo.relation.domain.group.RelationGroup;
import java.lang.Long;

import java.util.Optional;

public interface RelationGroupQueryRepository extends QueryRepository<RelationGroup, Long> {

    RelationGroup getById(Long id);
}
