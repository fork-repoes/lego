package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.CancelActionCommand;
import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.MarkActionCommand;
import com.geekhalo.like.domain.MarkActionContext;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
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

    public void like(Long userId, String targetType, Long targetId){
        MarkActionCommand markActionCommand = buildMarkActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<MarkActionCommand, MarkActionContext, LikeAction>syncerFor(this.likeActionRepository)
                .loadBy(command ->this.likeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(command -> MarkActionContext.apply(command))
                .instanceBy(context -> LikeAction.create(context))
                .update(((likeAction, context) -> likeAction.mark(context)))
                .exe(markActionCommand);
    }


    public void unLike(Long userId, String targetType, Long targetId){
        CancelActionCommand cancelActionCommand = buildCancelActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<LikeAction, CancelActionCommand, CancelActionContext>updaterFor(this.likeActionRepository)
                .loadBy(context -> this.likeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .update((like, context) -> like.cancel(context))
                .exe(cancelActionCommand);
    }


    public void dislike(Long userId, String targetType, Long targetId){
        MarkActionCommand markActionCommand = buildMarkActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<MarkActionCommand, MarkActionContext, DislikeAction>syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(command -> MarkActionContext.apply(command))
                .instanceBy(context -> DislikeAction.create(context))
                .update((dislikeAction, context) -> dislikeAction.mark(context))
                .exe(markActionCommand);
    }

    public void unDislike(Long userId, String targetType, Long targetId){
        CancelActionCommand cancelActionCommand = buildCancelActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<DislikeAction, CancelActionCommand, CancelActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .update((dislikeAction, context) -> dislikeAction.cancel(context))
                .exe(cancelActionCommand);
    }

    private CancelActionCommand buildCancelActionCommand(Long userId, String targetType, Long targetId) {
        return CancelActionCommand.apply(userId, targetType, targetId);
    }

    private MarkActionCommand buildMarkActionCommand(Long userId, String targetType, Long targetId) {
        return MarkActionCommand.apply(userId, targetType, targetId);
    }

}
