package com.geekhalo.relation.domain.group.enable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnableRelationGroupContext{
    private EnableRelationGroupCommand command;

    private EnableRelationGroupContext(EnableRelationGroupCommand command){
         this.command = command;
    }

    public static EnableRelationGroupContext apply(EnableRelationGroupCommand command) {
        EnableRelationGroupContext context = new EnableRelationGroupContext(command);
        return context;
    }
}
