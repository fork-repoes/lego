package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedRelationGroupCommandRepository
    extends RelationGroupCommandRepository, JpaRepository<RelationGroup, Long> {

    @Override
    default RelationGroup sync(RelationGroup group){
        return save(group);
    }
}
