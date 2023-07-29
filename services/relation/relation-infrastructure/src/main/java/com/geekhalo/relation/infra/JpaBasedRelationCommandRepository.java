package com.geekhalo.relation.infra;

import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationCommandRepository;
import com.geekhalo.relation.domain.relation.RelationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface JpaBasedRelationCommandRepository
    extends RelationCommandRepository, JpaRepository<Relation, Long> {

    @Override
    @Transactional(readOnly = false)
    default Relation sync(Relation relation){
        if (relation.getId() == null) {
            return save(relation);
        }else {
            updateRelation(relation);
            return relation;
        }
    }


    @Modifying
    @Query("update Relation r " +
            "set " +
                "r.status = :#{#p.status}, " +
                "r.groupId = :#{#p.groupId}," +
                "r.inBlackList = :#{#p.inBlackList}," +
                "r.updateAt = :#{#p.updateAt}," +
                "r.vsn = r.vsn + 1 " +
            "where " +
            " r.id = :#{#p.id} and r.vsn = :#{#p.vsn} and r.key.owner = :#{#p.key.owner} ")
    void updateRelation(@Param("p") Relation relation);

    @Override
    default Optional<Relation> findByKey(RelationKey relationKey){
        return getByKeyOwnerAndKeyRecipient(relationKey.getOwner(), relationKey.getRecipient());
    }

    Optional<Relation> getByKeyOwnerAndKeyRecipient(Long owner, Long recipient);
}
