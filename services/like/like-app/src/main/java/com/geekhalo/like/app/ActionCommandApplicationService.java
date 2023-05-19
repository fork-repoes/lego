package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.support.AbstractCommandService;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.MarkActionContext;
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

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    public void like(Long userId, String targetType, Long targetId){
        MarkActionContext markActionContext = buildMarkActionContext(userId, targetType, targetId);
        this.<Long, LikeAction, MarkActionContext>syncerFor(this.likeActionRepository)
                .loadBy(context ->this.likeActionRepository.getByUserIdAndTarget(userId, targetType, targetId))
                .instanceBy(context -> LikeAction.create(context))
                .update(((likeAction, context) -> likeAction.mark(context)))
                .exe(markActionContext);
    }


    public void unLike(Long userId, String targetType, Long targetId){
        CancelActionContext cancelActionContext = buildCancelActionContext(userId, targetType, targetId);
        this.<Long, LikeAction, CancelActionContext>updaterFor(this.likeActionRepository)
                .loadBy(context -> this.likeActionRepository.getByUserIdAndTarget(userId, targetType, targetId))
                .update((like, context) -> like.cancel(context))
                .exe(cancelActionContext);
    }


    public void dislike(Long userId, String targetType, Long targetId){
        MarkActionContext markActionContext = buildMarkActionContext(userId, targetType, targetId);
        this.<Long, DislikeAction, MarkActionContext>syncerFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, targetType, targetId))
                .instanceBy(context -> DislikeAction.create(context))
                .update((dislikeAction, context) -> dislikeAction.mark(context))
                .exe(markActionContext);
    }

    public void unDislike(Long userId, String targetType, Long targetId){
        CancelActionContext cancelActionContext = buildCancelActionContext(userId, targetType, targetId);
        this.<Long, DislikeAction, CancelActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(context -> this.dislikeActionRepository.getByUserIdAndTarget(userId, targetType, targetId))
                .update((dislikeAction, context) -> dislikeAction.cancel(context))
                .exe(cancelActionContext);
    }

    private CancelActionContext buildCancelActionContext(Long userId, String targetType, Long targetId) {
        CancelActionContext context = CancelActionContext.apply(userId, targetType, targetId);
        return this.lazyLoadProxyFactory.createProxyFor(context);
    }

    private MarkActionContext buildMarkActionContext(Long userId, String targetType, Long targetId) {
        MarkActionContext context = MarkActionContext.apply(userId, targetType, targetId);

        return this.lazyLoadProxyFactory.createProxyFor(context);
    }

}
