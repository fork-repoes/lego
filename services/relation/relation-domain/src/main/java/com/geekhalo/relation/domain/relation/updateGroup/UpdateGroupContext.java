package com.geekhalo.relation.domain.relation.updateGroup;

import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.loader.LazyLoadGroupById;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateGroupContext{
    private UpdateGroupCommand command;

    @LazyLoadGroupById(id = "command.groupId")
    private RelationGroup group;

    private UpdateGroupContext(UpdateGroupCommand command){
         this.command = command;
    }

    public static UpdateGroupContext apply(UpdateGroupCommand command) {
        UpdateGroupContext context = new UpdateGroupContext(command);
        return context;
    }
}
