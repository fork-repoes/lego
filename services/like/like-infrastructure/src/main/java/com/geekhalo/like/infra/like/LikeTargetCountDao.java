package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.infra.BaseTargetCountDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeTargetCountDao
    extends BaseTargetCountDao<LikeTargetCount>,JpaRepository<LikeTargetCount, Long> {
}
