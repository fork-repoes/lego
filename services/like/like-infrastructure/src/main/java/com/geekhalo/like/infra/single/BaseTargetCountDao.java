package com.geekhalo.like.infra.single;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BaseTargetCountDao<C extends AbstractTargetCount> {
    Optional<C> getByTarget(ActionTarget target);

    List<C> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds);

    @Modifying
    @Query("update #{#entityName}  c set c.count = c.count + ?2 where c.target = ?1")
    void incr(ActionTarget target, long count);
}
