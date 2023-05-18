package com.geekhalo.like.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
public class ActionUser {
    @Column(name = "user_id", updatable = false)
    private Long userId;

    public void validate(){

    }
}
