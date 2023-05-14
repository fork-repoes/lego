package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter(AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class AbstractAction extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Embedded
    private ActionTarget target;

    @Column(name = "status")
    private ActionStatus status;

    protected void init(Long userId, ActionTarget target){
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(target != null);
        setUserId(userId);
        setTarget(target);
    }

    public void cancel(){
        if (getStatus() != ActionStatus.INVALID) {
            setStatus(ActionStatus.INVALID);
            addEvent(createCancelledEvent());
        }
    }

    public void mark(){
        if (getStatus() != ActionStatus.VALID) {
            setStatus(ActionStatus.VALID);
            addEvent(createMarkedEvent());
        }
    }

    protected abstract AbstractCancelledEvent<? extends AbstractAction> createCancelledEvent();

    protected abstract AbstractMarkedEvent<? extends AbstractAction> createMarkedEvent();

}
