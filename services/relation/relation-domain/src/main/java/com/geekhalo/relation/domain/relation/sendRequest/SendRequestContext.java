package com.geekhalo.relation.domain.relation.sendRequest;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.loader.LazyLoadGroupById;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendRequestContext{
    private SendRequestCommand command;

    @LazyLoadGroupById(id = "command.groupId")
    private RelationGroup relationGroup;

    private SendRequestContext(SendRequestCommand command){
         this.command = command;
    }

    public static SendRequestContext apply (SendRequestCommand command) {
        SendRequestContext context = new SendRequestContext(command);
        return context;
    }
}
