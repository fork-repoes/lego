package com.geekhalo.like;

import com.geekhalo.like.domain.user.ActionUser;
import com.geekhalo.like.domain.user.ActionUserLoader;
import com.geekhalo.like.domain.user.LoadActionUserByUserId;
import org.springframework.stereotype.Component;

@Component(value = LoadActionUserByUserId.BEAN_NAME)
public class TestActionUserLoader implements ActionUserLoader {
    @Override
    public ActionUser loadByUserId(Long userId) {
        if (userId == null || userId.longValue() < 0){
            return ActionUser.apply(userId, false);
        }else {
            return ActionUser.apply(userId);
        }
    }
}
