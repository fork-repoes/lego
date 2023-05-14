package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTargetCount extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ActionTarget target;

    @Column(name = "count")
    private Long count;
}
