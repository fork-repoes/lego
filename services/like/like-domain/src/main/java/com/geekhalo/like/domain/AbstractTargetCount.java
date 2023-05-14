package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.AbstractAggRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTargetCount extends AbstractAggRoot {
    private ActionTarget target;
    private Long count;
}
