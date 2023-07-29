package com.geekhalo.relation.domain.relation.acceptRequest;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.loader.LazyLoadGroupById;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AcceptRequestContext{
    private AcceptRequestCommand command;

    @LazyLoadGroupById(id = "command.groupId")
    private RelationGroup relationGroup;

    private AcceptRequestContext(AcceptRequestCommand command){
         this.command = command;
    }

    public static AcceptRequestContext apply (AcceptRequestCommand command) {
        AcceptRequestContext context = new AcceptRequestContext(command);
        return context;
    }
}
