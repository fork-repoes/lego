package com.geekhalo.relation.domain.acceptRequest;

import lombok.Data;

@Data
public class AcceptRequestContext{
    private final AcceptRequestCommand command;

    private AcceptRequestContext(AcceptRequestCommand command){
         this.command = command;
    }

    public static AcceptRequestContext apply (AcceptRequestCommand command) {
        AcceptRequestContext context = new AcceptRequestContext(command);
        return context;
    }
}
