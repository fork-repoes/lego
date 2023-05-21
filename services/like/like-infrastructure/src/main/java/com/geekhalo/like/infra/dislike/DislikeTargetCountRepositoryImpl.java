package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DislikeTargetCountRepositoryImpl
        implements DislikeTargetCountRepository {

    @Autowired
    private DislikeTargetCountDao dao;

    @Autowired
    private DislikeTargetCountCache cache;

    @Value("${target.count.dislike.enable:false}")
    @Getter
    private boolean cacheEnable;


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
        return this.cache.getFromCache(type, targetIds);
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
