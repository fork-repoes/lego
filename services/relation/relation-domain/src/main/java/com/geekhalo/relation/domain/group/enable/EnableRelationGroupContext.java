package com.geekhalo.relation.domain.group.enable;

import com.geekhalo.relation.domain.group.enable.EnableRelationGroupCommand; 
import lombok.Data;
import lombok.AllArgsConstructor;
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
