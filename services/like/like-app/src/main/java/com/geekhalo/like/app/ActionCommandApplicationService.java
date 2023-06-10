package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.CancelByIdActionCommand;
import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.MarkByIdActionCommand;
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
        MarkByIdActionCommand markActionCommand = buildMarkActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<MarkByIdActionCommand, MarkActionContext, LikeAction>syncerFor(this.likeActionRepository)
                .loadBy(command ->this.likeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(command -> MarkActionContext.apply(command))
                .instanceBy(context -> LikeAction.create(context))
                .update(((likeAction, context) -> likeAction.mark(context)))
                .exe(markActionCommand);
    }


    public void unLike(Long userId, String targetType, Long targetId){
        CancelByIdActionCommand cancelActionCommand = buildCancelActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<LikeAction, CancelByIdActionCommand, CancelActionContext>updaterFor(this.likeActionRepository)
                .loadBy(context -> this.likeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(cmd -> CancelActionContext.apply(cmd))
                .update((like, context) -> like.cancel(context))
                .exe(cancelActionCommand);
    }


    public void dislike(Long userId, String targetType, Long targetId){
        MarkByIdActionCommand markActionCommand = buildMarkActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<MarkByIdActionCommand, MarkActionContext, DislikeAction>syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(command -> MarkActionContext.apply(command))
                .instanceBy(context -> DislikeAction.create(context))
                .update((dislikeAction, context) -> dislikeAction.mark(context))
                .exe(markActionCommand);
    }

    public void unDislike(Long userId, String targetType, Long targetId){
        CancelByIdActionCommand cancelActionCommand = buildCancelActionCommand(userId, targetType, targetId);
        ActionUser actionUser = ActionUser.apply(userId);
        ActionTarget actionTarget = ActionTarget.apply(targetType, targetId);

        this.<DislikeAction, CancelByIdActionCommand, CancelActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserAndTarget(actionUser, actionTarget))
                .contextFactory(cmd -> CancelActionContext.apply(cmd))
                .update((dislikeAction, context) -> dislikeAction.cancel(context))
                .exe(cancelActionCommand);
    }

    private CancelByIdActionCommand buildCancelActionCommand(Long userId, String targetType, Long targetId) {
        return CancelByIdActionCommand.apply(userId, targetType, targetId);
    }

    private MarkByIdActionCommand buildMarkActionCommand(Long userId, String targetType, Long targetId) {
        return MarkByIdActionCommand.apply(userId, targetType, targetId);
    }

}
