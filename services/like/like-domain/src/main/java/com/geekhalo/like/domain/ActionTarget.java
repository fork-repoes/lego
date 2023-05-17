package com.geekhalo.like.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
public class ActionTarget {
    @Column(name = "target_type", updatable = false)
    private Integer type;

    @Column(name = "target_id", updatable = false)
    private Long id;

    public static ActionTarget apply(Integer type, Long id){
        ActionTarget actionTarget = new ActionTarget();
        actionTarget.setType(type);
        actionTarget.setId(id);
        return actionTarget;
    }
}
