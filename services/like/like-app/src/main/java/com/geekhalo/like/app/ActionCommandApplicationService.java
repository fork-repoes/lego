package com.geekhalo.like.app;

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
public class ActionCommandApplicationService{
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private LikeActionRepository likeActionRepository;

    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    public void like(Long userId, ActionTarget target){
        Optional<LikeAction> likeActionOptional = this.likeActionRepository.getByUserIdAndTarget(userId, target);
        LikeAction likeAction = likeActionOptional.orElseGet(()-> LikeAction.create(userId, target));
        likeAction.mark();

        saveAndPublishEvents(likeAction, this.eventPublisher);
    }

    public void unLike(Long userId, ActionTarget target){
        Optional<LikeAction> likeActionOptional = this.likeActionRepository.getByUserIdAndTarget(userId, target);
        likeActionOptional.ifPresent(likeAction -> {
            likeAction.cancel();
            saveAndPublishEvents(likeAction, this.eventPublisher);
        });
    }

    private void saveAndPublishEvents(LikeAction likeAction, ApplicationEventPublisher eventPublisher) {
        likeActionRepository.save(likeAction);
        likeAction.consumeAndClearEvent(domainEvent -> eventPublisher.publishEvent(domainEvent));
    }

    public void dislike(Long user, ActionTarget target){
        Optional<DislikeAction> dislikeActionOptional = this.dislikeActionRepository.getByUserIdAndTarget(user, target);

        DislikeAction dislikeAction = dislikeActionOptional.orElseGet(()->DislikeAction.create(user, target));
        dislikeAction.mark();

        saveAndPublishEvents(dislikeAction, this.eventPublisher);

    }

    public void unDislike(Long userId, ActionTarget target){
        Optional<DislikeAction> dislikeActionOptional = this.dislikeActionRepository.getByUserIdAndTarget(userId, target);
        dislikeActionOptional.ifPresent(dislikeAction -> {
            dislikeAction.cancel();
            saveAndPublishEvents(dislikeAction, this.eventPublisher);
        });
    }

    private void saveAndPublishEvents( DislikeAction dislikeAction, ApplicationEventPublisher eventPublisher) {
        dislikeActionRepository.save(dislikeAction);
        dislikeAction.consumeAndClearEvent(domainEvent -> eventPublisher.publishEvent(domainEvent));
    }
}
