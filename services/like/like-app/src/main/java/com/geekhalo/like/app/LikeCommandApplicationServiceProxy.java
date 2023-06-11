package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionCommand;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.like.UnlikeActionCommand;

@CommandServiceDefinition(
        domainClass = LikeAction.class,
        repositoryClass = LikeActionRepository.class
)
public interface LikeCommandApplicationServiceProxy extends LikeCommandApplicationService{

}
