package com.geekhalo.relation.domain.relation.receiveRequest;

import com.geekhalo.lego.core.command.CommandForSync;
import com.geekhalo.relation.domain.relation.RelationKey;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ReceiveRequestCommand implements CommandForSync<RelationKey> {
    private RelationKey key;

     public ReceiveRequestCommand(RelationKey key) {
        this.key = key;
    }

    @Override
    public RelationKey getKey() {
        return this.key;
    }

    public static ReceiveRequestCommand apply(RelationKey key){
        ReceiveRequestCommand command = new ReceiveRequestCommand(key); 
        return command;
    }
}
