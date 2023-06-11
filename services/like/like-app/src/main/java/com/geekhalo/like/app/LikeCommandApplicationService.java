package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.NoCommandService;
import com.geekhalo.like.domain.like.LikeActionCommand;
import com.geekhalo.like.domain.like.UnlikeActionCommand;

@NoCommandService
public interface LikeCommandApplicationService {
    void like(LikeActionCommand command);

    void unLike(UnlikeActionCommand command);
}
