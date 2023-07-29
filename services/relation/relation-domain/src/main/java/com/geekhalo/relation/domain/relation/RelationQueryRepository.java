package com.geekhalo.relation.domain.relation;


import com.geekhalo.lego.core.query.QueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RelationQueryRepository extends QueryRepository<Relation, Long> {
    Page<Relation> getByKeyOwner(Long owner, Pageable pageable);

    Page<Relation> getByKeyOwnerAndStatusIn(Long owner, List<RelationStatus> statuses, Pageable pageable);
}
