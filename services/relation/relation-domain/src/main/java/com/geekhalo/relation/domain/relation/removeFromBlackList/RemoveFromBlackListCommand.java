package com.geekhalo.relation.domain.relation.removeFromBlackList;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveFromBlackListCommand implements CommandForUpdateByKey<RelationKey> {

    private RelationKey key;

    public RemoveFromBlackListCommand(RelationKey key) {
        this.key = key;
     }

    @Override
    public  RelationKey getKey(){
        return this.key;
    }

    public static RemoveFromBlackListCommand apply(RelationKey key){
        RemoveFromBlackListCommand command = new RemoveFromBlackListCommand(key); 
        return command;
    }
}
