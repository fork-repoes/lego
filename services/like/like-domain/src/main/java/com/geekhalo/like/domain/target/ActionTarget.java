package com.geekhalo.like.domain.target;

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
public class ActionTarget {
    @Column(name = "target_type", updatable = false)
    private String type;

    @Column(name = "target_id", updatable = false)
    private Long id;

    @Transient
    private boolean valid;

    public static ActionTarget apply(String type, Long id){
        return apply(type, id, true);
    }

    public static ActionTarget apply(String type, Long id, boolean valid){
        ActionTarget actionTarget = new ActionTarget();
        actionTarget.setType(type);
        actionTarget.setId(id);
        actionTarget.setValid(valid);
        return actionTarget;
    }

    public boolean isValid(){
        return valid;
    }
}