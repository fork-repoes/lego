package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.like.domain.dislike.*;
import com.geekhalo.like.domain.like.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

//@Service
@Transactional
class ActionCommandApplicationServiceImpl extends AbstractCommandService
        implements LikeCommandApplicationService,
        DislikeCommandApplicationService {
    
    @Autowired
    private LikeActionRepository likeActionRepository;

    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    @Override
    public void like(LikeActionCommand command){
        this.<LikeAction, LikeActionCommand, LikeActionContext>syncerFor(this.likeActionRepository)
                .loadBy(cmd ->this.likeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(cmd -> LikeActionContext.apply(cmd))
                .instanceBy(context -> LikeAction.create(context))
                .update(((likeAction, context) -> likeAction.like(context)))
                .exe(command);
    }


    @Override
    public void unLike(UnlikeActionCommand command){

        this.<LikeAction, UnlikeActionCommand, UnlikeActionContext>updaterFor(this.likeActionRepository)
                .loadBy(cmd -> this.likeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(cmd -> UnlikeActionContext.apply(cmd))
                .update((like, context) -> like.unlike(context))
                .exe(command);
    }


    @Override
    public void dislike(DislikeActionCommand command){

        this.<DislikeAction, DislikeActionCommand, DislikeActionContext>syncerFor(this.dislikeActionRepository)
                .loadBy(cmd -> this.dislikeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(cmd -> DislikeActionContext.apply(cmd))
                .instanceBy(context -> DislikeAction.create(context))
                .update((dislikeAction, context) -> dislikeAction.like(context))
                .exe(command);
    }

    @Override
    public void unDislike(UndislikeActionCommand command){

        this.<DislikeAction, UndislikeActionCommand, UndislikeActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(cmd -> this.dislikeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(cmd -> UndislikeActionContext.apply(cmd))
                .update((dislikeAction, context) -> dislikeAction.unlike(context))
                .exe(command);
    }
}
