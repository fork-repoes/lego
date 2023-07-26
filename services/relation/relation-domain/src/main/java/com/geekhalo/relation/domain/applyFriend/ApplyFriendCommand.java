package com.geekhalo.relation.domain.applyFriend;

import com.geekhalo.lego.core.command.CommandForSync;
import java.lang.Long;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyFriendCommand implements CommandForSync<Long> {
    private final Long key;

     public ApplyFriendCommand(Long key) {
        this.key = key;
    }

    @Override
    public Long getKey() {
        return this.key;
    }

    public static ApplyFriendCommand apply(Long key){
        ApplyFriendCommand command = new ApplyFriendCommand(key); 
        return command;
    }
}
