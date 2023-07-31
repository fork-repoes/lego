package com.geekhalo.relation.domain.relation.updateGroup;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateGroupCommand implements CommandForUpdateByKey<RelationKey> {

    private RelationKey key;

    private Long groupId;

    public UpdateGroupCommand(RelationKey key) {
        this.key = key;
     }

    @Override
    public  RelationKey getKey(){
        return this.key;
    }

    public static UpdateGroupCommand apply(RelationKey key){
        UpdateGroupCommand command = new UpdateGroupCommand(key); 
        return command;
    }
}
