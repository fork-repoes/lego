package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.NoQueryService;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

@NoQueryService
public interface LikeQueryApplicationService {
    List<LikeAction> getLikeByUserAndType(ActionUser user, String type);
}
