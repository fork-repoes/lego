package com.geekhalo.relation.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.geekhalo.relation.domain.applyFriend.ApplyFriendContext;
import com.geekhalo.relation.domain.applyFriend.FriendAppliedEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Relation extends AbstractAggRoot { 
    @Id
    private Long id;

    @Embedded
    @Setter(AccessLevel.PRIVATE)
    private RelationKey key;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PRIVATE)
    private RelationStatus status;



    public static Relation create(ApplyFriendContext context){
        return new Relation();
    }

    public void applyFriend(ApplyFriendContext context) {
        //添加代码
        addEvent(new FriendAppliedEvent(this));
    }
}