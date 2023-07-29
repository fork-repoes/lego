package com.geekhalo.relation.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationCommandRepository;
import com.geekhalo.relation.domain.relation.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.relation.addToBlackList.AddToBlackListCommand;
import com.geekhalo.relation.domain.relation.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.relation.receiveRequest.ReceiveRequestCommand;
import com.geekhalo.relation.domain.relation.removeFromBlackList.RemoveFromBlackListCommand;
import com.geekhalo.relation.domain.relation.sendRequest.SendRequestCommand;
import com.geekhalo.relation.domain.relation.updateGroup.UpdateGroupCommand;

@CommandServiceDefinition(
        domainClass = Relation.class,
        repositoryClass = RelationCommandRepository.class
)
public interface RelationCommandApplication {


    void sendRequest(SendRequestCommand command);

    void receiveRequest(ReceiveRequestCommand command);

    void acceptRequest(AcceptRequestCommand command);

    void cancelRequest(CancelRequestCommand command);

    void updateGroup(UpdateGroupCommand command);

    void addToBlackList(AddToBlackListCommand command);

    void removeFromBlackList(RemoveFromBlackListCommand command);
}
