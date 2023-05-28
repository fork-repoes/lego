package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.user.ActionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionQueryApplicationService {
    @Autowired
    private LikeActionRepository likeActionRepository;
    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    public List<LikeAction> getLikeByUserAndType(Long userId, String type) {
        ActionUser user = ActionUser.apply(userId);
        return this.likeActionRepository.getByUserAndTargetType(user, type);
    }

    public List<DislikeAction> getDislikeByUserAndType(Long userId, String type) {
        ActionUser user = ActionUser.apply(userId);
        return this.dislikeActionRepository.getByUserAndTargetType(user, type);
    }

}
