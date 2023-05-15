package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.ActionTarget;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
                .instance(context -> LikeAction.create(userId, target))
                .update(((likeAction, context) -> likeAction.mark()))
                .call(null);
    }

    public void unLike(Long userId, ActionTarget target){
        this.updaterFor(this.likeActionRepository)
                .loader(context -> this.likeActionRepository.getByUserIdAndTarget(userId, target))
                .update((like, context) -> like.cancel())
                .call(null);
    }


    public void dislike(Long user, ActionTarget target){
        this.syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(user, target))
                .instance(context -> DislikeAction.create(user, target))
                .update((dislikeAction, context) -> dislikeAction.mark())
                .call(null);
    }

    public void unDislike(Long userId, ActionTarget target){
        this.updaterFor(this.dislikeActionRepository)
                .loader(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, target))
                .update((dislikeAction, context) -> dislikeAction.cancel())
                .call(null);
    }
}
