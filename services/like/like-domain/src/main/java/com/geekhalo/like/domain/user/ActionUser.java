package com.geekhalo.like.domain.user;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionUser {
    @Column(name = "user_id", updatable = false)
    private Long userId;

    public void validate(){

    }

    public static ActionUser apply(Long userId){
        Preconditions.checkArgument(userId != null);
        ActionUser actionUser = new ActionUser();
        actionUser.setUserId(userId);
        return actionUser;
    }
}
