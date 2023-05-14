package com.geekhalo.like.app;

import com.geekhalo.like.domain.ActionTarget;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TargetCountCommandApplicationService {
    @Autowired
    private LikeTargetCountRepository likeTargetCountRepository;

    @Autowired
    private DislikeTargetCountRepository dislikeTargetCountRepository;

    public void incrLike(ActionTarget target, int count){
        this.likeTargetCountRepository.incr(target, count);
    }

    public void incrDislike(ActionTarget target, int count){
        this.dislikeTargetCountRepository.incr(target, count);
    }
}
