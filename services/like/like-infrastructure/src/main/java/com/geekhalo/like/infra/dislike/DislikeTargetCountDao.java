package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.infra.support.BaseTargetCountDao;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DislikeTargetCountDao
    extends BaseTargetCountDao<DislikeTargetCount>,
        JpaRepository<DislikeTargetCount, Long> {
}
