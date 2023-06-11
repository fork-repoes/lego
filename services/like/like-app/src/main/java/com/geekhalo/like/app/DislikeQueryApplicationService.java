package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.NoQueryService;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

@NoQueryService
public interface DislikeQueryApplicationService {
    List<DislikeAction> getDislikeByUserAndType(ActionUser user, String type);
}
