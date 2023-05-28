package com.geekhalo.like.domain.user;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionUser {
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Transient
    private boolean valid;

    public boolean isValid(){
        return this.valid;
    }
    public static ActionUser apply(Long userId){
        return apply(userId, true);
    }

    public static ActionUser apply(Long userId, boolean valid){
        Preconditions.checkArgument(userId != null);
        ActionUser actionUser = new ActionUser();
        actionUser.setUserId(userId);
        actionUser.setValid(valid);
        return actionUser;
    }
}
