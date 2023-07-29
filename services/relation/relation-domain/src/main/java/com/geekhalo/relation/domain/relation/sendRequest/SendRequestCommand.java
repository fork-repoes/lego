package com.geekhalo.relation.domain.relation.sendRequest;

import com.geekhalo.lego.core.command.CommandForSync;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SendRequestCommand implements CommandForSync<RelationKey> {
    private RelationKey key;

     public SendRequestCommand(RelationKey key) {
        this.key = key;
    }

    @Override
    public RelationKey getKey() {
        return this.key;
    }

    public static SendRequestCommand apply(RelationKey key){
        SendRequestCommand command = new SendRequestCommand(key); 
        return command;
    }
}
