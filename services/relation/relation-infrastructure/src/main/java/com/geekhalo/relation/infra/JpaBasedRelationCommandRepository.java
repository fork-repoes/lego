package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.Relation;
import com.geekhalo.relation.domain.RelationCommandRepository;
import com.geekhalo.relation.domain.RelationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBasedRelationCommandRepository
    extends RelationCommandRepository, JpaRepository<Relation, Long> {

    @Override
    default Relation sync(Relation relation){
        return save(relation);
    }

    @Override
    default Optional<Relation> findByKey(RelationKey relationKey){
        return getByKeyOwnerAndKeyRecipient(relationKey.getOwner(), relationKey.getRecipient());
    }

    Optional<Relation> getByKeyOwnerAndKeyRecipient(Long owner, Long recipient);
}
