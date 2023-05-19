package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
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

    @Embedded
    private ActionUser user;

    @Embedded
    private ActionTarget target;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActionStatus status;

    protected void init(AbstractActionContext context){
        Preconditions.checkArgument(context != null);
        setUser(context.getActionUser());
        setTarget(context.getActionTarget());
    }

    public void cancel(CancelActionContext context){
        if (getStatus() != ActionStatus.INVALID) {
            setStatus(ActionStatus.INVALID);
            addEvent(createCancelledEvent());
        }
    }

    public void mark(MarkActionContext context){
        if (getStatus() != ActionStatus.VALID) {
            setStatus(ActionStatus.VALID);
            addEvent(createMarkedEvent());
        }
    }

    protected abstract AbstractCancelledEvent<? extends AbstractAction> createCancelledEvent();

    protected abstract AbstractMarkedEvent<? extends AbstractAction> createMarkedEvent();

}
