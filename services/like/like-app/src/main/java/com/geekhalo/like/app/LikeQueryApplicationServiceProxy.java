package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;

@QueryServiceDefinition(
        repositoryClass = LikeActionRepository.class,
        domainClass = LikeAction.class
)
public interface LikeQueryApplicationServiceProxy extends LikeQueryApplicationService{
}
