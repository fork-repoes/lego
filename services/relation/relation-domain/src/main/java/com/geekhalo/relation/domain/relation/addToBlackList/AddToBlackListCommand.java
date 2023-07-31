package com.geekhalo.relation.domain.relation.addToBlackList;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToBlackListCommand implements CommandForUpdateByKey<RelationKey> {

    private RelationKey key;

    public AddToBlackListCommand(RelationKey key) {
        this.key = key;
     }

    @Override
    public  RelationKey getKey(){
        return this.key;
    }

    public static AddToBlackListCommand apply(RelationKey key){
        AddToBlackListCommand command = new AddToBlackListCommand(key); 
        return command;
    }
}
