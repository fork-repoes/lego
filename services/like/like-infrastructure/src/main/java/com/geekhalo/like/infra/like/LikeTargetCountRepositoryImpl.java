package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.infra.support.TargetCountCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LikeTargetCountRepositoryImpl implements LikeTargetCountRepository {
    @Autowired
    private TargetCountCache<LikeTargetCount> cache;

    @Autowired
    private LikeTargetCountDao dao;

    @Override
    public Optional<LikeTargetCount> getByTarget(ActionTarget target) {
        return dao.getByTarget(target);
    }

    @Override
    public void incr(ActionTarget target, long count) {
        this.cache.incr(target, count);
        this.dao.incr(target, count);
    }

    @Override
    public List<LikeTargetCount> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return this.cache.getByTargetTypeAndTargetIdIn(type, targetIds);
    }

    @Override
    public LikeTargetCount sync(LikeTargetCount entity) {
        this.cache.sync(entity);
        return this.dao.save(entity);
    }

    @Override
    public Optional<LikeTargetCount> findById(Long aLong) {
        return this.dao.findById(aLong);
    }
}
