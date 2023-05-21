package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.infra.BaseTargetCountDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DislikeTargetCountDao
    extends BaseTargetCountDao<DislikeTargetCount>,
        JpaRepository<DislikeTargetCount, Long> {
}
