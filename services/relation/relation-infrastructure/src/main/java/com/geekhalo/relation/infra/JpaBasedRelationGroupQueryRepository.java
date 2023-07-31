package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedRelationGroupQueryRepository
    extends RelationGroupQueryRepository, JpaRepository<RelationGroup, Long> {
}
