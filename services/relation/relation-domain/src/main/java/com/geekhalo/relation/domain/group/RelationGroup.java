package com.geekhalo.relation.domain.group;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupCommand;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupContext;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupContext;
import com.geekhalo.relation.domain.group.create.RelationGroupCreatedEvent;
import com.geekhalo.relation.domain.group.disable.DisableRelationGroupCommand;
import com.geekhalo.relation.domain.group.disable.DisableRelationGroupContext;
import com.geekhalo.relation.domain.group.disable.DisableRelationGroupContext;
import com.geekhalo.relation.domain.group.disable.RelationGroupDisabledEvent;
import com.geekhalo.relation.domain.group.enable.EnableRelationGroupCommand;
import com.geekhalo.relation.domain.group.enable.EnableRelationGroupContext;
import com.geekhalo.relation.domain.group.enable.EnableRelationGroupContext;
import com.geekhalo.relation.domain.group.enable.RelationGroupEnabledEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class RelationGroup extends AbstractAggRoot { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    private Long owner;

    @Enumerated(EnumType.STRING)
    private RelationGroupStatus status;

    private String name;

    private String descr;


    public static RelationGroup create(CreateRelationGroupContext context) {
        CreateRelationGroupCommand command = context.getCommand();
        RelationGroup group = new RelationGroup();
        group.setOwner(command.getUser());
        group.setName(command.getName());
        group.setDescr(command.getDescr());
        group.init();
        return group;
    }

    private void init(){
        setStatus(RelationGroupStatus.ENABLE);
        addEvent(new RelationGroupCreatedEvent(this));
    }

    public void enable(EnableRelationGroupContext context) {
        if (getStatus() == RelationGroupStatus.DISABLE) {
            setStatus(RelationGroupStatus.ENABLE);
            addEvent(new RelationGroupEnabledEvent(this));
        }
    }

    public void disable(DisableRelationGroupContext context) {
        if (getStatus() == RelationGroupStatus.ENABLE) {
            setStatus(RelationGroupStatus.DISABLE);
            addEvent(new RelationGroupDisabledEvent(this));
        }
    }
}