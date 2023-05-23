package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.infra.support.BaseTargetCountDao;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface LikeTargetCountDao
    extends BaseTargetCountDao<LikeTargetCount>,JpaRepository<LikeTargetCount, Long> {
}
