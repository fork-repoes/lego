package com.geekhalo.relation.domain.relation.addToBlackList;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddToBlackListContext{
    private AddToBlackListCommand command;

    private AddToBlackListContext(AddToBlackListCommand command){
         this.command = command;
    }

    public static AddToBlackListContext apply(AddToBlackListCommand command) {
        AddToBlackListContext context = new AddToBlackListContext(command);
        return context;
    }
}
