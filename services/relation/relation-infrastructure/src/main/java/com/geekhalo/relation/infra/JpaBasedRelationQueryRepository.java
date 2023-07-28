package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.Relation;
import com.geekhalo.relation.domain.RelationQueryRepository;
import com.geekhalo.relation.domain.RelationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBasedRelationQueryRepository
        extends RelationQueryRepository, JpaRepository<Relation, Long> {


}
