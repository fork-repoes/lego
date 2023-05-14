package com.geekhalo.like.domain;

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
    private Integer type;

    @Column(name = "target_id", updatable = false)
    private Long id;
}
