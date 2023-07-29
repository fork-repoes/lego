package com.geekhalo.relation.domain.relation.acceptRequest;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptRequestCommand implements CommandForUpdateByKey<RelationKey> {

    private RelationKey key;

    public AcceptRequestCommand(RelationKey key) {
        this.key = key;
     }

    @Override
    public  RelationKey getKey(){
        return this.key;
    }

    public static AcceptRequestCommand apply(RelationKey key){
        AcceptRequestCommand command = new AcceptRequestCommand(key); 
        return command;
    }
}
