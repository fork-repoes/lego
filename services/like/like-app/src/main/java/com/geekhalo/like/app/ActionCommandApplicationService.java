package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.MarkActionContext;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.target.ActionTarget;
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
        MarkActionContext markActionContext = buildMarkActionContext(userId, target);
        this.<Long, LikeAction, MarkActionContext>syncerFor(this.likeActionRepository)
                .loadBy(context ->this.likeActionRepository.getByUserIdAndTarget(userId, target))
                .instanceBy(context -> LikeAction.create(context))
                .update(((likeAction, context) -> likeAction.mark(context)))
                .exe(markActionContext);
    }


    public void unLike(Long userId, ActionTarget target){
        CancelActionContext cancelActionContext = buildCancelActionContext(userId, target);
        this.<Long, LikeAction, CancelActionContext>updaterFor(this.likeActionRepository)
                .loadBy(context -> this.likeActionRepository.getByUserIdAndTarget(userId, target))
                .update((like, context) -> like.cancel(context))
                .exe(cancelActionContext);
    }


    public void dislike(Long userId, ActionTarget target){
        MarkActionContext markActionContext = buildMarkActionContext(userId, target);
        this.<Long, DislikeAction, MarkActionContext>syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, target))
                .instanceBy(context -> DislikeAction.create(context))
                .update((dislikeAction, context) -> dislikeAction.mark(context))
                .exe(markActionContext);
    }

    public void unDislike(Long userId, ActionTarget target){
        CancelActionContext cancelActionContext = buildCancelActionContext(userId, target);
        this.<Long, DislikeAction, CancelActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, target))
                .update((dislikeAction, context) -> dislikeAction.cancel(context))
                .exe(cancelActionContext);
    }

    private CancelActionContext buildCancelActionContext(Long userId, ActionTarget target) {
        CancelActionContext context = CancelActionContext.apply(userId, target.getType(), target.getId());
        return context;
    }

    private MarkActionContext buildMarkActionContext(Long userId, ActionTarget target) {
        MarkActionContext context = MarkActionContext.apply(userId, target.getType(), target.getId());

        return context;
    }

}
