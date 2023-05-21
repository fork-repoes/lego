package com.geekhalo.like.infra.single.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.infra.single.BaseTargetCountDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeTargetCountDao
    extends BaseTargetCountDao<LikeTargetCount>,JpaRepository<LikeTargetCount, Long> {
}
