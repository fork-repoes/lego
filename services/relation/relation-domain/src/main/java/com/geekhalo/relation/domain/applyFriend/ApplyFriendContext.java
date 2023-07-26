package com.geekhalo.relation.domain.applyFriend;

import com.geekhalo.relation.domain.applyFriend.ApplyFriendCommand; 
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
public class ApplyFriendContext{
    private final ApplyFriendCommand command;

    private ApplyFriendContext(ApplyFriendCommand command){
         this.command = command;
    }

    public static ApplyFriendContext apply (ApplyFriendCommand command) {
        ApplyFriendContext context = new ApplyFriendContext(command);
        return context;
    }
}
