package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

@QueryServiceDefinition(
        repositoryClass = DislikeActionRepository.class,
        domainClass = DislikeAction.class
)
public interface DislikeQueryApplicationServiceProxy extends DislikeQueryApplicationService{

}
