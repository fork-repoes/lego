package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractTargetCount extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ActionTarget target;

    @Column(name = "count")
    private Long count;

    protected void init(ActionTarget target, long count) {
        Preconditions.checkArgument(target != null);
        setTarget(target);
        setCount(count);
    }
}
