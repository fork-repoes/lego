package com.geekhalo.relation.domain.cancelRequest;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;
import com.geekhalo.relation.domain.RelationKey;
import lombok.Data;

@Data
public class CancelRequestCommand implements CommandForUpdateByKey<RelationKey> {

    private final RelationKey key;

    public CancelRequestCommand(RelationKey key) {
        this.key = key;
     }

    @Override
    public  RelationKey getKey(){
        return this.key;
    }

    public static CancelRequestCommand apply(RelationKey key){
        CancelRequestCommand command = new CancelRequestCommand(key); 
        return command;
    }
}
