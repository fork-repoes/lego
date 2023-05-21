package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.infra.support.TargetCountCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DislikeTargetCountRepositoryImpl
        implements DislikeTargetCountRepository {

    @Autowired
    private DislikeTargetCountDao dao;

    @Autowired
    private TargetCountCache<DislikeTargetCount> cache;


    @Override
    public Optional<DislikeTargetCount> getByTarget(ActionTarget target) {
        return this.dao.getByTarget(target);
    }

    @Override
    public void incr(ActionTarget target, long count) {
        this.cache.incr(target, count);
        this.dao.incr(target, count);
    }

    @Override
    public List<DislikeTargetCount> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds) {
        return this.cache.getByTargetTypeAndTargetIdIn(type, targetIds);
    }

    @Override
    public DislikeTargetCount sync(DislikeTargetCount entity) {
        this.cache.sync(entity);
        return this.dao.save(entity);
    }

    @Override
    public Optional<DislikeTargetCount> findById(Long aLong) {
        return this.dao.findById(aLong);
    }
}
