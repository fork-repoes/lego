package com.geekhalo.relation.domain.relation.removeFromBlackList;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RemoveFromBlackListContext{
    private RemoveFromBlackListCommand command;

    private RemoveFromBlackListContext(RemoveFromBlackListCommand command){
         this.command = command;
    }

    public static RemoveFromBlackListContext apply(RemoveFromBlackListCommand command) {
        RemoveFromBlackListContext context = new RemoveFromBlackListContext(command);
        return context;
    }
}
