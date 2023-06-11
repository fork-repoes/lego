package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

@QueryServiceDefinition(
        repositoryClass = LikeActionRepository.class,
        domainClass = LikeAction.class
)
public interface LikeQueryApplicationServiceProxy extends LikeQueryApplicationService{
}
