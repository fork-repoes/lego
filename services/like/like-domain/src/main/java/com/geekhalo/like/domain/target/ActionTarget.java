package com.geekhalo.like.domain.target;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
public class ActionTarget {
    @Column(name = "target_type", updatable = false)
    private String type;

    @Column(name = "target_id", updatable = false)
    private Long id;

    public static ActionTarget apply(String type, Long id){
        ActionTarget actionTarget = new ActionTarget();
        actionTarget.setType(type);
        actionTarget.setId(id);
        return actionTarget;
    }

    public void validate(){

    }
}