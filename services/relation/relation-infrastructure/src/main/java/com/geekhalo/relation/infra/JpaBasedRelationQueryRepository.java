package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedRelationQueryRepository
        extends RelationQueryRepository, JpaRepository<Relation, Long> {


}
