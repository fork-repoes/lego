package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.NoCommandService;
import com.geekhalo.like.domain.dislike.DislikeActionCommand;
import com.geekhalo.like.domain.dislike.UndislikeActionCommand;

@NoCommandService
public interface DislikeCommandApplicationService {
    void dislike(DislikeActionCommand command);

    void unDislike(UndislikeActionCommand command);
}
