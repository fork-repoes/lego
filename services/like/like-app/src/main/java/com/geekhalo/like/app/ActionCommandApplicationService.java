package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.ActionTarget;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ActionCommandApplicationService extends AbstractCommandService {
    
    @Autowired
    private LikeActionRepository likeActionRepository;

    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    public void like(Long userId, ActionTarget target){
        this.syncerFor(this.likeActionRepository)
                .loadBy(context ->this.likeActionRepository.getByUserIdAndTarget(userId, target))
                .instanceBy(context -> LikeAction.create(userId, target))
                .update(((likeAction, context) -> likeAction.mark()))
                .exe(null);
    }

    public void unLike(Long userId, ActionTarget target){
        this.updaterFor(this.likeActionRepository)
                .loadBy(context -> this.likeActionRepository.getByUserIdAndTarget(userId, target))
                .update((like, context) -> like.cancel())
                .exe(null);
    }


    public void dislike(Long user, ActionTarget target){
        this.syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(user, target))
                .instanceBy(context -> DislikeAction.create(user, target))
                .update((dislikeAction, context) -> dislikeAction.mark())
                .exe(null);
    }

    public void unDislike(Long userId, ActionTarget target){
        this.updaterFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, target))
                .update((dislikeAction, context) -> dislikeAction.cancel())
                .exe(null);
    }
}
