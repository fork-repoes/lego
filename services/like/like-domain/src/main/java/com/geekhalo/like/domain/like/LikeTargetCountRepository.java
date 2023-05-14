package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractTargetCountRepository;
import com.geekhalo.like.domain.ActionTarget;

public interface LikeTargetCountRepository extends AbstractTargetCountRepository<LikeTargetCount> {
    void incr(ActionTarget target, int count);
}
